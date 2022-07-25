package com.example.autoescuelapp.ui.clases.solicitarClases

import com.example.autoescuelapp.domain.Clase

interface SolicitarClasesContract {
    sealed class Event {
        data class SolicitarClase(val clase: Clase, val selected: Boolean) : Event()
        object GetClasesFecha : Event()
        object ErrorMostrado : Event()
        object MensajeMostrado : Event()
    }

    data class State(
        val clases: List<Clase> = emptyList(),
        val error: String? = null,
        val mensaje: String? = null,
    )
}