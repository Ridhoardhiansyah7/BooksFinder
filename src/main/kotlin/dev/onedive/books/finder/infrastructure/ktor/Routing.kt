package dev.onedive.books.finder.infrastructure.ktor

import dev.onedive.books.finder.application.usecase.BooksUseCase
import dev.onedive.books.finder.domain.model.Book
import dev.onedive.books.finder.infrastructure.impl.BooksRepositoryImpl
import dev.onedive.books.finder.utils.RequestResult
import dev.onedive.books.finder.utils.transformErrorDataToJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {

        val booksRepository = BooksRepositoryImpl()
        val booksUseCase = BooksUseCase(booksRepository)

        post("/books/save") {

            val params = call.receiveParameters()
            val bookName = params["bookName"]
            val bookImageUrl = params["bookImageUrl"]
            val bookDescription = params["bookDescription"]
            val author = params["author"]


            if (
                bookName.isNullOrBlank()
                or bookImageUrl.isNullOrBlank()
                or bookDescription.isNullOrBlank()
                or author.isNullOrBlank()

            ) {

                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message =
                        transformErrorDataToJson(listOf(
                            "1.Nama buku / isi dari parameter bookName harus ada dan unik.",
                            "2.Foto buku / isi dari parameter bookImageUrl harus ada.",
                            "3.Deskripsi buku / isi dari parameter bookDescription harus ada.",
                            "4.Author buku / isi dari parameter author harus ada."
                        )
                    )
                )

                return@post

            } else {

                val saveBooks = booksUseCase.saveBooks(Book(bookName!!, bookImageUrl!!, bookDescription!!, author!!))

                when (saveBooks) {

                    is RequestResult.Success -> {
                        call.respond(HttpStatusCode.OK, saveBooks.data)
                    }

                    is RequestResult.Error -> {
                        call.respond(saveBooks.errorCode,saveBooks.error)

                    }

                }

            }

        }


        get("/books/{bookName}") {

            val bookName = call.parameters["bookName"] ?: " "

            if (bookName.isBlank()) {

                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = transformErrorDataToJson("1. parameter id(bookName) tidak boleh null / blank"),
                )

                return@get

            } else {

                when (val bookResult = booksUseCase.getBookById(bookName)) {

                    is RequestResult.Success -> {
                        call.respond(HttpStatusCode.OK, bookResult.data)
                    }

                    is RequestResult.Error -> {
                        call.respond(bookResult.errorCode,bookResult.error)

                    }


                }

            }


        }

        get("/books") {

            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val perPage = call.request.queryParameters["perPage"]?.toIntOrNull() ?: 10

            if (page < 1 || perPage < 1) {

                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = transformErrorDataToJson(
                        listOf(
                            "1.Pastikan query parameter page tidak kosong / tidak kurang dari satu.",
                            "2.Pastikan query parameter perpage tidak kosong / tidak kurang dari satu"
                        )
                    )
                )

            } else {

                when(val resultList = booksUseCase.getBooksByPageWithLimitData(page.toLong(), perPage.toLong())) {

                    is RequestResult.Success -> {
                        call.respond(HttpStatusCode.OK,resultList.data)
                    }

                    is RequestResult.Error -> {
                        call.respond(resultList.errorCode,resultList.error)
                    }

                }

            }

        }

        put("/books/update/{bookName}") {

            val bookName = call.parameters["bookName"]

            val receiveObject = call.receive<Book>()

            if (bookName.isNullOrBlank()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = transformErrorDataToJson("1.Pastikan data parameter bookName ada dan value tidak kosong")
                )
                return@put

            } else {

                when(val update = booksUseCase.updateBooksById(bookName,receiveObject)) {

                    is RequestResult.Success -> {
                        call.respond(HttpStatusCode.OK, update.data)
                    }

                    is RequestResult.Error -> {
                        call.respond(update.errorCode,update.error)
                    }

                }

            }

        }

        delete("/books/del/{bookName}") {

            val bookNameParams = call.parameters["bookName"]

            if (bookNameParams.isNullOrBlank()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = transformErrorDataToJson("1.Pastikan data parameter bookName ada dan value tidak kosong")
                )
                return@delete

            } else {

                when (val deleteById = booksUseCase.deleteBookById(bookNameParams)) {

                    is RequestResult.Success -> {
                        call.respond(HttpStatusCode.OK, deleteById.data)
                    }

                    is RequestResult.Error -> {
                        call.respond(deleteById.errorCode,deleteById.error)
                    }

                }

            }

        }

    }

}
