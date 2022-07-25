package com.example.autoescuelapp.domain

import java.time.LocalDate


data class Clase(
    val fecha: LocalDate?,
    val horarioInicio: String,
    val duracion: String,
    val dniProfesor: String,
    val dniAlumno: String,
    val id: Int? = 0,
)
