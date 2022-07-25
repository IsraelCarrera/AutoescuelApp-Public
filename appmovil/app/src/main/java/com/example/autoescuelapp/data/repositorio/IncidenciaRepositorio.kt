package com.example.autoescuelapp.data.repositorio

import com.example.autoescuelapp.data.sources.remote.RemoteIncidenciaDataSource
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.domain.Incidencia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class IncidenciaRepositorio @Inject constructor(
    private val remoteIncidenciaDataSource: RemoteIncidenciaDataSource,
) {
    fun postIncidencia(incidencia: Incidencia): Flow<ResultadoLlamada<Unit>> {
        return flow {
            emit(remoteIncidenciaDataSource.postIncidencia(incidencia))
        }.flowOn(Dispatchers.IO)
    }
}