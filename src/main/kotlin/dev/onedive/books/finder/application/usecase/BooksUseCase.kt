package dev.onedive.books.finder.application.usecase

import dev.onedive.books.finder.domain.model.Book
import dev.onedive.books.finder.domain.repository.BooksRepository

class BooksUseCase ( private val booksRepository: BooksRepository ) {

    fun saveBooks(book: Book) = booksRepository.saveBooks(book)

    fun getBookById(bookName : String) = booksRepository.getBookById(bookName)

    fun getBooksByPageWithLimitData(page : Long, perPage : Long) = booksRepository.getBooksByPageWithLimitData(page, perPage)

    fun updateBooksById(bookName : String, updateObject : Book) = booksRepository.updateBooksById(bookName, updateObject)

    fun deleteBookById(bookName : String) = booksRepository.deleteBooksById(bookName)


}