package dev.onedive.books.finder.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import dev.onedive.books.finder.domain.model.Book

private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

internal fun transformSuccessDataToJson(book: Book): JsonObject {

    val json =  """
        {
          "result": {
            "isSuccessfully": true,
            "data": ${gson.toJson(book)}
          }
        }
    """.trimIndent()

    return transformJsonStringToJsonObject(json)
}

internal fun transformSuccessDataToJson(data: List<Book>): JsonObject {

    val json =  """
       {
         "result": {
           "isSuccessfully": true,
           "data": ${gson.toJson(data)}
         }
       } 
    """.trimIndent()

    return transformJsonStringToJsonObject(json)
}

internal fun transformSuccessDataToJson(str: String): JsonObject {

    val json =  """
        {
          "result": {
            "isSuccessfully": true,
            "data": ${gson.toJson(str)}
          }
        }
    """.trimIndent()

    return transformJsonStringToJsonObject(json)
}

internal fun transformErrorDataToJson(errorCausedAsStr: String): JsonObject {

    val json =  """
       {
         "result": {
           "isSuccessfully": false,
           "error": ${gson.toJson(listOf(errorCausedAsStr))}
         }
       } 
    """.trimIndent()

    return transformJsonStringToJsonObject(json)
}

internal fun transformErrorDataToJson(errorCausedAsList: List<String>): JsonObject {

    val json =  """
       {
         "result": {
           "isSuccessfully": false,
           "error": ${gson.toJson(errorCausedAsList)}
         }
       } 
    """.trimIndent()

    return transformJsonStringToJsonObject(json)
}

private fun transformJsonStringToJsonObject(js : String) = gson.fromJson(js,JsonObject::class.java)