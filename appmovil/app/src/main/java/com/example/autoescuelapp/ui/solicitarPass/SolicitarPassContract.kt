package com.example.autoescuelapp.ui.solicitarPass

interface SolicitarPassContract {
    sealed class Event {
        data class ReiniciarPass(val dni: String) : Event()
        object ErrorMostrado : Event()
        object MensajeMostrado : Event()
    }

    data class State(
        val error: String? = null,
        val mensaje: String? = null,
    )
}