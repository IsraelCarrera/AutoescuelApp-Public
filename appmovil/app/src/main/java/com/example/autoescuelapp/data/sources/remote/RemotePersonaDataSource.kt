package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.modelo.PersonaData
import com.example.autoescuelapp.data.utils.RespuestaAPI
import com.example.autoescuelapp.data.utils.toPersona
import com.example.autoescuelapp.data.utils.toStr
import com.example.autoescuelapp.domain.PersonaLogin
import javax.inject.Inject

class RemotePersonaDataSource @Inject constructor(
    private val personaAPI: PersonaAPI,
) : RespuestaAPI() {
    suspend fun hacerLog(personaLogin: PersonaLogin) =
        safeApiCall(
            apiCall = { personaAPI.hacerLogin(personaLogin.dni, personaLogin.pass) },
            transform = PersonaData::toPersona
        )

    suspend fun getAlumnoByIdClase(idClase: Int) =
        safeApiCall(
            apiCall = { personaAPI.getAlumnoByIdClase(idClase) },
            transform = PersonaData::toPersona
        )

    suspend fun getProfesorByIdClase(idClase: Int) =
        safeApiCall(
            apiCall = { personaAPI.getProfesorByIdClase(idClase) },
            transform = PersonaData::toPersona
        )

    suspend fun getPersonaByDni(dni: String) =
        safeApiCall(
            apiCall = { personaAPI.getPersonaByDni(dni) },
            transform = PersonaData::toPersona
        )

    suspend fun cambiarPass(dni: String, passVieja: String, passNueva: String) =
        safeApiCall(
            apiCall = { personaAPI.cambiarPass(dni, passVieja, passNueva) },
            transform = { it.toStr() }
        )

    suspend fun reiniciarPass(dni: String) =
        safeApiCall(
            apiCall = { personaAPI.reiniciarPass(dni) },
            transform = { it.toStr() }
        )
}