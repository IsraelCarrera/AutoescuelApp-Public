package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.utils.RespuestaAPI
import com.example.autoescuelapp.data.utils.toClase
import com.example.autoescuelapp.data.utils.toClaseData
import com.example.autoescuelapp.data.utils.toStr
import com.example.autoescuelapp.domain.Clase
import java.time.LocalDate
import javax.inject.Inject

class RemoteClaseDataSource @Inject constructor(
    private val claseAPI: ClaseAPI,
) : RespuestaAPI() {
    suspend fun getClasesByDateAlumno(dniAlumno: String) =
        safeApiCall(
            apiCall = { claseAPI.getClasesAlumnoDates(dniAlumno) },
            transform = { it.map { clase -> clase.toClase() } }
        )

    suspend fun getClasesByDateProfesor(dniProfesor: String) =
        safeApiCall(
            apiCall = { claseAPI.getClasesProfesorDates(dniProfesor) },
            transform = { it.map { clase -> clase.toClase() } }
        )

    suspend fun registrarClase(clase: Clase) =
        safeApiCall(
            apiCall = { claseAPI.addClase(clase.toClaseData()) },
            transform = {}
        )

    suspend fun getClaseId(id: Int) =
        safeApiCall(
            apiCall = { claseAPI.getClaseId(id) },
            transform = { it.toClase() }
        )

    suspend fun getClasesByDateAlumnoBefore(dniAlumno: String) =
        safeApiCall(
            apiCall = { claseAPI.getClasesAlumnoDatesBefore(dniAlumno) },
            transform = { it.map { clase -> clase.toClase() } }
        )

    suspend fun getClasesByDateProfesorBefore(dniProfesor: String) =
        safeApiCall(
            apiCall = { claseAPI.getClasesProfesorDatesBefore(dniProfesor) },
            transform = { it.map { clase -> clase.toClase() } }
        )

    suspend fun deleteClase(id: Int) =
        safeApiCall(
            apiCall = { claseAPI.deleteClase(id) },
            transform = { it.toStr() }
        )

    suspend fun deleteClaseDates(dniProfesor: String, fecha: LocalDate) =
        safeApiCall(
            apiCall = { claseAPI.deleteClaseByDates(dniProfesor, fecha.toString()) },
            transform = { it.toStr() }
        )
}