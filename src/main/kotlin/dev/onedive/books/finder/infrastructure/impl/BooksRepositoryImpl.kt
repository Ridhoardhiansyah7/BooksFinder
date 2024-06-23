package dev.onedive.books.finder.infrastructure.impl

import com.google.gson.JsonObject
import dev.onedive.books.finder.domain.model.Book
import dev.onedive.books.finder.domain.model.Books
import dev.onedive.books.finder.domain.repository.BooksRepository
import dev.onedive.books.finder.utils.RequestResult
import dev.onedive.books.finder.utils.transformErrorDataToJson
import dev.onedive.books.finder.utils.transformSuccessDataToJson
import io.ktor.http.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class BooksRepositoryImpl : BooksRepository {

    override fun saveBooks(book: Book): RequestResult<JsonObject, JsonObject> {

        return try {

            transaction {
                Books.insert {
                    it[bookName] = book.bookName
                    it[bookImageUrl] = book.bookImageUrl
                    it[bookDescription] = book.bookDescription
                    it[author] = book.author
                }
            }

            RequestResult.Success(transformSuccessDataToJson(book))

        } catch (e : ExposedSQLException) {
            RequestResult.Error(
                errorCode = HttpStatusCode.Conflict,
                error = transformErrorDataToJson("1.Data buku dengan nama ${book.bookName} sudah ada, tidak boleh duplicate"),
            )
        }


    }

    override fun getBookById(bookName: String): RequestResult<JsonObject, JsonObject> {

        val book = transaction {

            Books.select { Books.bookName eq bookName }
                .map {
                    Book(it[Books.bookName], it[Books.bookImageUrl], it[Books.bookDescription], it[Books.author])
                }
                .singleOrNull()

        }

        return if (book == null) {
            RequestResult.Error(
                errorCode = HttpStatusCode.NotFound,
                error = transformErrorDataToJson("1.Data buku dengan nama $bookName tidak ada"),
            )
        } else {
            RequestResult.Success(transformSuccessDataToJson(book))
        }

    }

    override fun getBooksByPageWithLimitData(page: Long, perPage: Long): RequestResult<JsonObject,JsonObject> {

        return try {

            val resultAll = transaction {

                Books.selectAll()
                    .limit(perPage.toInt(), (page - 1) * perPage)
                    .map {
                        Book(
                            it[Books.bookName], it[Books.bookImageUrl],
                            it[Books.bookDescription], it[Books.author]
                        )
                    }
                    .toList()

            }

            RequestResult.Success(transformSuccessDataToJson(resultAll))

        }catch (e : ExposedSQLException) {
            RequestResult.Error(
                errorCode = HttpStatusCode.InternalServerError,
                error = transformErrorDataToJson("error with sql code : ${e.errorCode}")
            )
        }

    }

    override fun updateBooksById(bookName: String,updateObject : Book): RequestResult<JsonObject, JsonObject> {

        return try {

            val update = transaction {
                Books.update( { Books.bookName eq bookName }) {
                    it[Books.bookName] = updateObject.bookName
                    it[bookImageUrl] = updateObject.bookImageUrl
                    it[bookDescription] = updateObject.bookDescription
                    it[author] = updateObject.author

                }
            }

            return if (update == 0) {
                RequestResult.Error(
                    errorCode = HttpStatusCode.NotFound,
                    error = transformErrorDataToJson("1.Gagal update data dikarnakan buku dengan nama $bookName tidak ada")
                )
            } else {
                RequestResult.Success(transformSuccessDataToJson(updateObject))
            }


        } catch (e : ExposedSQLException) {
            RequestResult.Error(
                errorCode = HttpStatusCode.InternalServerError,
                error = transformErrorDataToJson(listOf(
                    "1.Periksa apakah id(bookName) ada di database",
                    "2.Periksa apakah database terkoneksi dengan baik",
                    "3.Error sql code : ${e.errorCode} for debugging"
                ))
            )
        }

    }

    override fun deleteBooksById(bookName: String): RequestResult<JsonObject, JsonObject> {

        val errorMessage = "Data tidak dapat dihapus dikarnakan Tidak ada data buku dengan nama $bookName ."

        val delete = transaction {
            Books.deleteWhere { Books.bookName eq bookName }
        }

        return if (delete >= 1) {
            RequestResult.Success(transformSuccessDataToJson("Data dengan id(bookName) : $bookName berhasil di hapus"))
        } else {
            RequestResult.Error(
                errorCode = HttpStatusCode.NotFound,
                error = transformErrorDataToJson(errorMessage)
            )
        }

    }

}
