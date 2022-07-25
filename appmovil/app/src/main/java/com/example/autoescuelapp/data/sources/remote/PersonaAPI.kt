package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.modelo.InfoData
import com.example.autoescuelapp.data.modelo.PersonaData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface PersonaAPI {
    @GET("usuario/log")
    suspend fun hacerLogin(
        @Query("dni") dni: String,
        @Query("pass") pass: String,
    ): Response<PersonaData>

    @GET("usuario/get/clase/alumno")
    suspend fun getAlumnoByIdClase(
        @Query("idClase") idClase: Int,
    ): Response<PersonaData>

    @GET("usuario/get/clase/profesor")
    suspend fun getProfesorByIdClase(
        @Query("idClase") idClase: Int,
    ): Response<PersonaData>

    @GET("usuario/get/dni")
    suspend fun getPersonaByDni(
        @Query("dni") dni: String,
    ): Response<PersonaData>

    @PUT("usuario/cambiarPass")
    suspend fun cambiarPass(
        @Query("dni") dni: String,
        @Query("passVieja") passVieja: String,
        @Query("passNueva") passNueva: String,
    ): Response<InfoData>

    @GET("usuario/reiniciarPass")
    suspend fun reiniciarPass(
        @Query("dni") dni: String,
    ): Response<InfoData>
}