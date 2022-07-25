package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.utils.RespuestaAPI
import com.example.autoescuelapp.data.utils.toFeedback
import com.example.autoescuelapp.data.utils.toFeedbackData
import com.example.autoescuelapp.data.utils.toStr
import com.example.autoescuelapp.domain.Feedback
import javax.inject.Inject

class RemoteFeedbackDataSource @Inject constructor(
    private val feedbackAPI: FeedbackAPI,
) : RespuestaAPI() {
    suspend fun getFeedbackByClass(idClase: Int) =
        safeApiCall(
            apiCall = { feedbackAPI.getFeedback(idClase) },
            transform = { it.toFeedback() }
        )

    suspend fun postFeedback(feedback: Feedback) =
        safeApiCall(
            apiCall = { feedbackAPI.addFeedback(feedback.toFeedbackData()) },
            transform = {}
        )

    suspend fun deleteFeedback(idClase: Int) =
        safeApiCall(
            apiCall = { feedbackAPI.deleteFeedback(idClase) },
            transform = { it.toStr() }
        )

    suspend fun updateFeedback(feedback: Feedback) =
        safeApiCall(
            apiCall = { feedbackAPI.updateFeedback(feedback.toFeedbackData()) },
            transform = { it.toStr() }
        )
}