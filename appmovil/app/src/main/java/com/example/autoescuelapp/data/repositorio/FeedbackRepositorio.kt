package com.example.autoescuelapp.data.repositorio

import com.example.autoescuelapp.data.sources.remote.RemoteFeedbackDataSource
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.domain.Feedback
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class FeedbackRepositorio @Inject constructor(
    private val remoteFeedbackDataSource: RemoteFeedbackDataSource,
) {
    fun getFeedback(idClase: Int): Flow<ResultadoLlamada<Feedback>> {
        return flow {
            emit(remoteFeedbackDataSource.getFeedbackByClass(idClase))
        }.flowOn(Dispatchers.IO)
    }

    fun addFeedback(feedback: Feedback): Flow<ResultadoLlamada<Unit>> {
        return flow {
            emit(remoteFeedbackDataSource.postFeedback(feedback))
        }.flowOn(Dispatchers.IO)
    }

    fun deleteFeedback(idClase: Int): Flow<ResultadoLlamada<String>> {
        return flow {
            emit(remoteFeedbackDataSource.deleteFeedback(idClase))
        }.flowOn(Dispatchers.IO)
    }

    fun updateFeedback(feedback: Feedback): Flow<ResultadoLlamada<String>> {
        return flow {
            emit(remoteFeedbackDataSource.updateFeedback(feedback))
        }.flowOn(Dispatchers.IO)
    }
}