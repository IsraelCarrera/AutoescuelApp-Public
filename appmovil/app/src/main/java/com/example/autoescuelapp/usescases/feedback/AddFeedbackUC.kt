package com.example.autoescuelapp.usescases.feedback

import com.example.autoescuelapp.data.repositorio.ClaseRepositorio
import com.example.autoescuelapp.data.repositorio.FeedbackRepositorio
import com.example.autoescuelapp.domain.Clase
import com.example.autoescuelapp.domain.Feedback
import javax.inject.Inject

class AddFeedbackUC @Inject constructor(private val feedback: FeedbackRepositorio)  {
    fun invoke(fb: Feedback)  = feedback.addFeedback(fb)
}