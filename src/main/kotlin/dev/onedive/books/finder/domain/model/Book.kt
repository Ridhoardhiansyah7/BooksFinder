package dev.onedive.books.finder.domain.model

import org.jetbrains.exposed.sql.Table

data class Book(
    val bookName: String,
    val bookImageUrl: String,
    val bookDescription: String,
    val author: String
)

object Books : Table() {
    val bookName = varchar("bookName", length = 70).primaryKey()
    val bookImageUrl = varchar("bookImageUrl", length = 255)
    val bookDescription = varchar("bookDescription", length = 255)
    val author = varchar("author", length = 255)
}

