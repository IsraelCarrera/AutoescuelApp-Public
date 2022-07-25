package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.utils.RespuestaAPI
import com.example.autoescuelapp.data.utils.toIncidenciaData
import com.example.autoescuelapp.domain.Incidencia
import javax.inject.Inject

class RemoteIncidenciaDataSource @Inject constructor(
    private val incidenciaAPI: IncidenciaAPI,
) : RespuestaAPI() {

    suspend fun postIncidencia(incidencia: Incidencia) =
        safeApiCall(
            apiCall = { incidenciaAPI.addIncidencia(incidencia.toIncidenciaData()) },
            transform = { }
        )
}