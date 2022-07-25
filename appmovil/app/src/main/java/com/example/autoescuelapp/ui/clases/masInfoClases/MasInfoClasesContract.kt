package com.example.autoescuelapp.ui.clases.masInfoClases

import com.example.autoescuelapp.domain.Clase
import com.example.autoescuelapp.domain.Feedback
import com.example.autoescuelapp.domain.Persona

interface MasInfoClasesContract {
    sealed class Event {
        data class ClasePorId(val id: Int) : Event()
        data class BuscarProfesor(val id: Int) : Event()
        data class BuscarAlumno(val id: Int) : Event()
        data class DeleteClaseId(val id: Int) : Event()
        data class GetFeedback(val id: Int) : Event()
        data class AddFeedback(val feedback: Feedback?) : Event()
        data class DeleteFeedback(val id: Int) : Event()
        data class MandarError(val error: String) : Event()
        object ErrorMostrado : Event()
        object MensajeMostrado : Event()
    }

    data class State(
        val profesor: Persona? = null,
        val alumno: Persona? = null,
        val clase: Clase? = null,
        val feedback: Feedback? = null,
        val error: String? = null,
        val mensaje: String? = null,
    )
}