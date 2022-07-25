package com.example.autoescuelapp.ui.cambiarPass


interface CambiarPassContract {
    sealed class Event {
        data class CambiarPass(val dni: String, val passAnterior: String, val passNueva: String) :
            Event()

        data class MandarError(val error: String) : Event()
        object ErrorMostrado : Event()
        object MensajeMostrado : Event()
    }

    data class State(
        val error: String? = null,
        val mensaje: String? = null,
    )
}