package com.example.autoescuelapp.usescases.clase

import com.example.autoescuelapp.data.repositorio.ClaseRepositorio
import javax.inject.Inject

class GetClasesProfesorDateUC @Inject constructor(private val clase: ClaseRepositorio) {
    fun invoke(dniProfe: String) = clase.getClaseProfesorDates(dniProfe)
}