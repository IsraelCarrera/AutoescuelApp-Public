package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.modelo.ClaseData
import com.example.autoescuelapp.data.modelo.ClaseList
import com.example.autoescuelapp.data.modelo.InfoData
import retrofit2.Response
import retrofit2.http.*

interface ClaseAPI {
    @GET("clase/get/dates/alumno")
    suspend fun getClasesAlumnoDates(
        @Query("dniAlumno") dni: String,
    ): Response<ClaseList>

    @GET("clase/get/dates/profesor")
    suspend fun getClasesProfesorDates(
        @Query("dniProfesor") dni: String,
    ): Response<ClaseList>

    @POST("clase/registrar")
    suspend fun addClase(
        @Body clase: ClaseData,
    ): Response<Unit>

    @GET("clase/{id}")
    suspend fun getClaseId(
        @Path("id") id: Int,
    ): Response<ClaseData>

    @GET("clase/get/dates/before/alumno")
    suspend fun getClasesAlumnoDatesBefore(
        @Query("dniAlumno") dni: String,
    ): Response<ClaseList>

    @GET("clase/get/dates/before/profesor")
    suspend fun getClasesProfesorDatesBefore(
        @Query("dniProfesor") dni: String,
    ): Response<ClaseList>

    @DELETE("clase/borrar")
    suspend fun deleteClase(
        @Query("id") id: Int,
    ): Response<InfoData>

    @DELETE("clase/borrarDate")
    suspend fun deleteClaseByDates(
        @Query("dniProfesor") dniProfesor: String,
        @Query("fecha") fecha: String,
    ): Response<InfoData>
}