package dev.onedive.books.finder.infrastructure.ktor

import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {}
        json()
    }

 /*   routing {

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }

    }*/


}
