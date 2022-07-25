package com.example.autoescuelapp.usescases.clase

import com.example.autoescuelapp.data.repositorio.ClaseRepositorio
import javax.inject.Inject

class GetClasesProfesorDateBeforeUC @Inject constructor(private val clase: ClaseRepositorio) {
    fun invoke(dniProfesor: String) = clase.getClaseProfesorDatesBefore(dniProfesor)
}