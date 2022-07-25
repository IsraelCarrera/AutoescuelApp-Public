package com.example.autoescuelapp.usescases.clase

import com.example.autoescuelapp.data.repositorio.ClaseRepositorio
import java.time.LocalDate
import javax.inject.Inject

class DeleteClaseByDatesUC @Inject constructor(private val clase: ClaseRepositorio) {
    fun invoke(dniProfesor: String, fecha: LocalDate) = clase.deleteClaseByDate(dniProfesor, fecha)
}