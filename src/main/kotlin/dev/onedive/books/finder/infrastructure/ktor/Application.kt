package dev.onedive.books.finder.infrastructure.ktor

import dev.onedive.books.finder.utils.configureDatabase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main(args: Array<String>) {

    embeddedServer(Netty, port = 8080){
        module()
    }.start(wait = true)

}

fun Application.module() {
    configureSerialization()
    configureDatabase()
    configureRouting()
}
