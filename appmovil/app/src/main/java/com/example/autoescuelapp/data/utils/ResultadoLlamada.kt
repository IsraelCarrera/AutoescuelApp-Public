package com.example.autoescuelapp.data.utils

sealed class ResultadoLlamada<T>(
    val data: T? = null,
    val message: String? = null,
) {
    //Si es exitosa.
    class Success<T>(data: T) : ResultadoLlamada<T>(data)

    //Si es un error.
    class Error<T>(message: String, data: T? = null) : ResultadoLlamada<T>(data, message)

    fun <R> map(transform: (data: T?) -> R): ResultadoLlamada<R> =
        when (this) {
            is Error -> Error(message!!, transform(data))
            is Success -> Success(transform(data))
        }
}