package com.example.autoescuelapp.domain

data class Feedback(
    val titulo: String,
    val cuerpo: String,
    val puntuacion: Int,
    val idClase: Int,
    val dniAlumno: String,
)
