package com.example.autoescuelapp.data.utils

import com.example.autoescuelapp.data.modelo.ErrorServer
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response

abstract class RespuestaAPI {
    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<R>,
        transform: (R) -> T,
    ): ResultadoLlamada<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ResultadoLlamada.Success(transform(body))
                }
            } else {
                if (response.errorBody()?.contentType()
                        ?.equals("application/json".toMediaType())!!
                ) {
                    val g = Gson()
                    val fallo =
                        g.fromJson(response.errorBody()!!.charStream(), ErrorServer::class.java)
                    return error(fallo.mensaje)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): ResultadoLlamada<T> =
        ResultadoLlamada.Error(errorMessage)

}