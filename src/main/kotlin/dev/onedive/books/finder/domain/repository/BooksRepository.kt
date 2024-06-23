package dev.onedive.books.finder.domain.repository

import com.google.gson.JsonObject
import dev.onedive.books.finder.domain.model.Book
import dev.onedive.books.finder.utils.RequestResult


interface BooksRepository  {

    fun saveBooks(book: Book) : RequestResult<JsonObject,JsonObject>

    fun getBookById (bookName : String) : RequestResult<JsonObject,JsonObject>

    fun getBooksByPageWithLimitData (page : Long, perPage : Long) : RequestResult<JsonObject,JsonObject>

    fun updateBooksById (bookName : String,updateObject : Book) : RequestResult <JsonObject,JsonObject>

    fun deleteBooksById (bookName : String) : RequestResult <JsonObject,JsonObject>

}