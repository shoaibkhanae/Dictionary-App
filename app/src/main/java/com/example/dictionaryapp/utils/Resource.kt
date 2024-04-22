package com.example.dictionaryapp.utils

sealed class Resource<T>(val data: T? = null, val error: String? = null) {
    class Loading<T>: Resource<T>()
    class Success<T>(data: T? = null) : Resource<T>(data = data)
    class Error<T>(message: String) : Resource<T>(error = message)
}
