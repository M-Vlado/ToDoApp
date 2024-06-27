package sk.vmproject.todoapp.domain.utils

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