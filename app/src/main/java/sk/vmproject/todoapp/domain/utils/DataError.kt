package sk.vmproject.todoapp.domain.utils

sealed interface DataError : Error {
    enum class Network : DataError {
        BAD_REQUEST,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
    }

    enum class Local : DataError {
        DISK_FULL
    }
}