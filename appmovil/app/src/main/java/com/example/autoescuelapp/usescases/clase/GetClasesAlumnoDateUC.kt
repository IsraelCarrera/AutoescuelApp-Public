package com.example.autoescuelapp.usescases.clase

import com.example.autoescuelapp.data.repositorio.ClaseRepositorio
import javax.inject.Inject

class GetClasesAlumnoDateUC @Inject constructor(private val clase: ClaseRepositorio) {
    fun invoke(dniAlumno: String) = clase.getClaseAlumnoDates(dniAlumno)
}