package com.atorrico.assignment.presentation.util

sealed class Result<out T>() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure<out T>(val exception: Exception? = null) : Result<T>()
}

inline fun <T, R> Result<T>.mapSuccess(mapper: (T) -> R) = when(this) {
    is Result.Success -> mapper(this.data)
    else -> this
}