package com.enriqueajin.newsapp.domain

typealias RootError = Error

sealed interface Result<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(val data: D): Result<D, E>
    data class Error<out D, out E: RootError>(val error: E): Result<D, E>
}

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        NOT_FOUND,
        FORBIDDEN,
        SERVICE_UNAVAILABLE,
        UNAUTHORIZED,
        CLIENT_ERROR,
        UNKNOWN_ERROR
    }
}

fun Int.asErrorResult() = when(this) {
    401 -> DataError.Network.UNAUTHORIZED
    403 -> DataError.Network.FORBIDDEN
    404 -> DataError.Network.NOT_FOUND
    408 -> DataError.Network.REQUEST_TIMEOUT
    429 -> DataError.Network.TOO_MANY_REQUESTS
    503 -> DataError.Network.SERVICE_UNAVAILABLE
    in 400..499 -> DataError.Network.CLIENT_ERROR
    in 500..599 -> DataError.Network.SERVER_ERROR
    else -> DataError.Network.UNKNOWN_ERROR
}
