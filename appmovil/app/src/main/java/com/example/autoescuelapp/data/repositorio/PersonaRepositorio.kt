package com.example.autoescuelapp.data.repositorio

import com.example.autoescuelapp.data.sources.remote.RemotePersonaDataSource
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.domain.Persona
import com.example.autoescuelapp.domain.PersonaLogin
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class PersonaRepositorio @Inject constructor(
    private val remotePersonaDataSource: RemotePersonaDataSource,
) {
    fun hacerLog(personaLogin: PersonaLogin): Flow<ResultadoLlamada<Persona>> {
        return flow {
            emit(remotePersonaDataSource.hacerLog(personaLogin))
        }.flowOn(Dispatchers.IO)
    }

    fun getAlumnoByIdClase(idClase: Int): Flow<ResultadoLlamada<Persona>> {
        return flow {
            emit(remotePersonaDataSource.getAlumnoByIdClase(idClase))
        }.flowOn(Dispatchers.IO)
    }

    fun getProfesorByIdClase(idClase: Int): Flow<ResultadoLlamada<Persona>> {
        return flow {
            emit(remotePersonaDataSource.getProfesorByIdClase(idClase))
        }.flowOn(Dispatchers.IO)
    }

    fun getPersonaByDni(dni: String): Flow<ResultadoLlamada<Persona>> {
        return flow {
            emit(remotePersonaDataSource.getPersonaByDni(dni))
        }.flowOn(Dispatchers.IO)
    }

    fun cambiarPass(
        dni: String,
        passVieja: String,
        passNueva: String,
    ): Flow<ResultadoLlamada<String>> {
        return flow {
            emit(remotePersonaDataSource.cambiarPass(dni, passVieja, passNueva))
        }.flowOn(Dispatchers.IO)
    }

    fun reiniciarPass(dni: String): Flow<ResultadoLlamada<String>> {
        return flow {
            emit(remotePersonaDataSource.reiniciarPass(dni))
        }.flowOn(Dispatchers.IO)
    }
}