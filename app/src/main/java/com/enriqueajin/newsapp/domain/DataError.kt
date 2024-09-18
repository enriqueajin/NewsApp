package com.enriqueajin.newsapp.domain

sealed interface DataError: Error {

    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
        UNAUTHORIZED,
        CONFLICT,
        UPGRADE_REQUIRED,
        UNKNOWN_HOST,
    }

    enum class Database: DataError {
        CONSTRAINT_VIOLATION,
        DATABASE_CORRUPTED,
        STORAGE_FULL,
        UNKNOWN,
    }
}