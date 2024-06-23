package dev.onedive.books.finder.utils

import io.ktor.http.*

sealed class RequestResult<T, EE> (
    private val resultData : T? = null,
    private val error: EE? = null
) {

    data class Success<T,EE> (val data : T ) : RequestResult<T,EE>(data)

    data class Error<T,EE> ( val errorCode : HttpStatusCode, val error : EE ) : RequestResult<T,EE>(error = error)

}