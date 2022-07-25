package com.example.autoescuelapp.ui.infoCoche

import com.example.autoescuelapp.domain.Coche

interface InfoCocheContract {
    sealed class Event {
        data class BuscarCoche(val matricula: String?) : Event()
        object ErrorMostrado : Event()
        object MensajeMostrado : Event()
    }

    data class State(
        val coche: Coche? = null,
        val error: String? = null,
        val mensaje: String? = null,
    )
}