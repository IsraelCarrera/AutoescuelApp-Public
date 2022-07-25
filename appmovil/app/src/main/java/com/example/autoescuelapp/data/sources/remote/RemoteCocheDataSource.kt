package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.utils.RespuestaAPI
import com.example.autoescuelapp.data.utils.toCoche
import javax.inject.Inject

class RemoteCocheDataSource @Inject constructor(
    private val cocheAPI: CocheAPI,
) : RespuestaAPI() {

    suspend fun getCocheByMatricula(matricula: String) =
        safeApiCall(
            apiCall = { cocheAPI.getCocheByMatricula(matricula) },
            transform = { it.toCoche() }
        )
}
