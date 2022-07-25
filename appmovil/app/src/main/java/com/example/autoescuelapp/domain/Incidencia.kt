package com.example.autoescuelapp.domain

import java.time.LocalDateTime

data class Incidencia(
    val id: Int = 0,
    val tituloIncidencia: String,
    val descripcion: String,
    val ubicacion: String,
    val fecha: LocalDateTime = LocalDateTime.now(),
    val dniProfesor: String,
    val matriculaCoche: String,
)
