package com.example.autoescuelapp.usescases.feedback

import com.example.autoescuelapp.data.repositorio.FeedbackRepositorio
import javax.inject.Inject

class DeleteFeedbackUC @Inject constructor(private val feedback: FeedbackRepositorio) {
    fun invoke(idClase: Int) = feedback.deleteFeedback(idClase)
}