package com.example.autoescuelapp.ui.clases.clasesProximas

import com.example.autoescuelapp.domain.Clase
import java.time.LocalDate

interface ProximasClasesContract {
    sealed class Event {
        data class ClasesAlumnoDate(val dniAlumno: String) : Event()
        data class ClasesProfesorDate(val dniProfesor: String) : Event()
        data class DeleteClasesByDate(val dniProfesor: String, val date: LocalDate) : Event()
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