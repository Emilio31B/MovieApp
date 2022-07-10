package com.example.movieapp.core.infra

sealed class NetworkResponse<out R> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val exception: Exception) : NetworkResponse<Nothing>()
}

val NetworkResponse<*>.succeeded
    get() = this is NetworkResponse.Success && data != null