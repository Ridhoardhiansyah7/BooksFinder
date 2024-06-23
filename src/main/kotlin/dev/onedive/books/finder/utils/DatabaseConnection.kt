package dev.onedive.books.finder.utils

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.onedive.books.finder.domain.model.Books
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.configureDatabase() {

    val hikariConfig = HikariConfig()

    hikariConfig.jdbcUrl = "jdbc:mysql://localhost:3306/books_finder" // change to your database name;
    hikariConfig.username = "root" // change to your mysql username;
    hikariConfig.password = "root" // change to your mysql password;
    hikariConfig.driverClassName = "com.mysql.cj.jdbc.Driver" // DON'T CHANGE THIS DRIVER CLASSES;
    hikariConfig.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

    hikariConfig.maximumPoolSize = 10
    hikariConfig.minimumIdle = 5
    hikariConfig.idleTimeout = 30_000
    hikariConfig.maxLifetime = 10 * 60_000

    val hikariDataSource = HikariDataSource(hikariConfig)
    Database.connect(datasource = hikariDataSource)

    transaction {
        SchemaUtils.create(Books)
    }

}
