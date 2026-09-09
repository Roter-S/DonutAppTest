package com.example.donutapptest.core.common

sealed interface Result<out T> {
    data class Success<out T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null, val message: UiText? = null) : Result<Nothing>
    data object Loading : Result<Nothing>
}
