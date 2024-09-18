package com.enriqueajin.newsapp.presentation

import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.the_request_timed_out
        )

        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.youve_hit_your_rate_limit
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Network.UNAUTHORIZED -> UiText.StringResource(
            R.string.unauthorized
        )

        DataError.Network.CONFLICT -> UiText.StringResource(
            R.string.conflict
        )

        DataError.Network.UPGRADE_REQUIRED -> UiText.StringResource(
            R.string.upgrade_required
        )

        DataError.Database.CONSTRAINT_VIOLATION -> UiText.StringResource(
            R.string.constraint_violation
        )
        DataError.Database.DATABASE_CORRUPTED -> UiText.StringResource(
            R.string.database_corrupted
        )
        DataError.Database.STORAGE_FULL -> UiText.StringResource(
            R.string.storage_full
        )
        DataError.Database.UNKNOWN -> UiText.StringResource(
            R.string.unknown_database
        )

        DataError.Network.UNKNOWN_HOST -> UiText.StringResource(
            R.string.unknown_host
        )
    }
}
//
//fun Result.Error<*, DataError>.asErrorUiText(): UiText {
//    return error.asUiText()
//}