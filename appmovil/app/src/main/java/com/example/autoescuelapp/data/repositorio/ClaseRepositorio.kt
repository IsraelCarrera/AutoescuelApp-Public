package com.example.autoescuelapp.data.repositorio

import com.example.autoescuelapp.data.sources.remote.RemoteClaseDataSource
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.domain.Clase
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import javax.inject.Inject

@ActivityRetainedScoped
class ClaseRepositorio @Inject constructor(
    private val remoteClaseDataSource: RemoteClaseDataSource,
) {
    fun getClaseAlumnoDates(dniAlumno: String): Flow<ResultadoLlamada<List<Clase>>> {
        return flow {
            emit(remoteClaseDataSource.getClasesByDateAlumno(dniAlumno))
        }.flowOn(Dispatchers.IO)
    }

    fun getClaseProfesorDates(dniProfesor: String): Flow<ResultadoLlamada<List<Clase>>> {
        return flow {
            emit(remoteClaseDataSource.getClasesByDateProfesor(dniProfesor))
        }.flowOn(Dispatchers.IO)
    }

    fun saveClase(clase: Clase): Flow<ResultadoLlamada<Unit>> {
        return flow {
            emit(remoteClaseDataSource.registrarClase(clase = clase))
        }.flowOn(Dispatchers.IO)
    }

    fun getClaseId(id: Int): Flow<ResultadoLlamada<Clase>> {
        return flow {
            emit(remoteClaseDataSource.getClaseId(id))
        }.flowOn(Dispatchers.IO)
    }

    fun getClaseAlumnoDatesBefore(dniAlumno: String): Flow<ResultadoLlamada<List<Clase>>> {
        return flow {
            emit(remoteClaseDataSource.getClasesByDateAlumnoBefore(dniAlumno))
        }.flowOn(Dispatchers.IO)
    }

    fun getClaseProfesorDatesBefore(dniProfesor: String): Flow<ResultadoLlamada<List<Clase>>> {
        return flow {
            emit(remoteClaseDataSource.getClasesByDateProfesorBefore(dniProfesor))
        }.flowOn(Dispatchers.IO)
    }

    fun deleteClase(id: Int): Flow<ResultadoLlamada<String>> {
        return flow {
            emit(remoteClaseDataSource.deleteClase(id))
        }.flowOn(Dispatchers.IO)
    }

    fun deleteClaseByDate(dniProfesor: String, fecha: LocalDate): Flow<ResultadoLlamada<String>> {
        return flow {
            emit(remoteClaseDataSource.deleteClaseDates(dniProfesor, fecha))
        }.flowOn(Dispatchers.IO)
    }
}