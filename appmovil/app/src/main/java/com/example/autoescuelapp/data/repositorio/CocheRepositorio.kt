package com.example.autoescuelapp.data.repositorio

import com.example.autoescuelapp.data.sources.remote.RemoteCocheDataSource
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.domain.Coche
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CocheRepositorio @Inject constructor(
    private val remoteCocheDataSource: RemoteCocheDataSource,
) {
    fun getCocheByMatricula(matricula: String): Flow<ResultadoLlamada<Coche>> {
        return flow {
            emit(remoteCocheDataSource.getCocheByMatricula(matricula))
        }.flowOn(Dispatchers.IO)
    }
}