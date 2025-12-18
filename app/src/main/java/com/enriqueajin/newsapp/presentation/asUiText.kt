package com.enriqueajin.newsapp.presentation

import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.presentation.UiText.*

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.UNAUTHORIZED -> StringResource(
            R.string.error_unauthorized
        )

        DataError.Network.FORBIDDEN -> StringResource(
            R.string.error_forbidden
        )

        DataError.Network.NOT_FOUND -> StringResource(
            R.string.error_not_found
        )

        DataError.Network.REQUEST_TIMEOUT -> StringResource(
            R.string.error_request_timeout
        )

        DataError.Network.TOO_MANY_REQUESTS -> StringResource(
            R.string.error_too_many_requests
        )

        DataError.Network.SERVICE_UNAVAILABLE -> StringResource(
            R.string.error_service_unavailable
        )

        DataError.Network.CLIENT_ERROR -> StringResource(
            R.string.error_client_generic
        )

        DataError.Network.SERVER_ERROR -> StringResource(
            R.string.error_server_generic
        )

        DataError.Network.UNKNOWN_ERROR -> StringResource(
            R.string.error_unknown
        )

        DataError.Network.NO_INTERNET -> StringResource(
            R.string.no_internet_connection
        )
    }
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}