package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.modelo.IncidenciaData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IncidenciaAPI {

    @POST("incidencia/registrar")
    suspend fun addIncidencia(
        @Body incidencia: IncidenciaData,
    ): Response<Unit>
}