package com.example.autoescuelapp.domain

import java.time.LocalDate

data class Coche(
    val marca: String,
    val matricula: String,
    val modelo: String,
    val proximaItv: LocalDate,
)
