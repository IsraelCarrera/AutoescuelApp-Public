package com.example.autoescuelapp.ui.notificarIncidencia

import com.example.autoescuelapp.domain.Incidencia

interface NotificarIncidenciaContract {
    sealed class Event {
        data class AddIncidencia(val incidencia: Incidencia) : Event()
        data class MandarError(val error: String) : Event()
        object ErrorMostrado : Event()
        object MensajeMostrado : Event()
    }

    data class State(
        val error: String? = null,
        val mensaje: String? = null,
    )
}