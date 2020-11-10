package com.github.tehras.data.client

import com.github.tehras.data.client.ResultWrapper.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(
        val code: Int? = null,
        val message: String? = null
    ) : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()

    fun asErrorMessage(): String {
        return when (this) {
            is Success -> error("Make sure you check the state is not Success.")
            is GenericError -> message ?: "Generic error"
            NetworkError -> "Network error."
        }
    }
}


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> NetworkError
                is HttpException -> {
                    GenericError(throwable.code(), extractMessage(throwable))
                }
                else -> GenericError(null, throwable.message)
            }
        }
    }
}

fun extractMessage(throwable: HttpException): String? {
    return Json { ignoreUnknownKeys = true }
        .decodeFromString<ErrorBody>(throwable.response()?.errorBody()?.string() ?: "{}")
        .message
}

@Serializable
private data class ErrorBody(
    val code: Int?,
    val message: String?
)
