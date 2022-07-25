package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.modelo.CocheData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocheAPI {
    @GET("coche/get/matricula")
    suspend fun getCocheByMatricula(
        @Query("matricula") dni: String,
    ): Response<CocheData>

}
