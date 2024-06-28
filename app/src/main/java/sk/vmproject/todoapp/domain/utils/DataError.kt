package sk.vmproject.todoapp.domain.utils

import sk.vmproject.todoapp.R
import sk.vmproject.todoapp.presentation.utils.UiText

sealed interface DataError : Error {
    enum class Network : DataError {
        NOT_FOUND,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
    }

    enum class Local : DataError {
        DISK_FULL
    }
}

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
        DataError.Network.NOT_FOUND -> UiText.StringResource(R.string.error_unknown)
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.error_unknown)
        DataError.Network.SERIALIZATION -> UiText.StringResource(R.string.error_unknown)
        DataError.Network.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
    }
}