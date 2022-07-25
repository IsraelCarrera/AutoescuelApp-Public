package com.example.autoescuelapp.ui.clases.clasesAnteriores

import com.example.autoescuelapp.domain.Clase

interface ClasesAnterioresContract {
    sealed class Event {
        data class ClasesAlumnoDateBefore(val dniAlumno: String) : Event()
        data class ClasesProfesorDateBefore(val dniProfesor: String) : Event()
        object ErrorMostrado : Event()
        object MensajeMostrado : Event()
    }

    data class State(
        val clasesAlumno: List<Clase> = emptyList(),
        val clasesProfesor: List<Clase> = emptyList(),
        val error: String? = null,
        val mensaje: String? = null,
    )
}