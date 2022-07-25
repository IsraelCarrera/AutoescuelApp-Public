package com.example.autoescuelapp.ui.infoPersona

import com.example.autoescuelapp.domain.Coche
import com.example.autoescuelapp.domain.Persona


interface InfoPersonaContract {
    sealed class Event {
        data class BuscarTutor(val dniTutor: String?) : Event()
        data class BuscarCoche(val matricula: String?) : Event()
        object ErrorMostrado : Event()
        object MensajeMostrado : Event()
    }

    data class State(
        val tutor: Persona? = null,
        val coche: Coche? = null,
        val error: String? = null,
        val mensaje: String? = null,
    )
}