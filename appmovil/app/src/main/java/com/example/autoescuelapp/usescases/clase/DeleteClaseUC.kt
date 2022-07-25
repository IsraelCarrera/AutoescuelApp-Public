package com.example.autoescuelapp.usescases.clase

import com.example.autoescuelapp.data.repositorio.ClaseRepositorio
import javax.inject.Inject

class DeleteClaseUC @Inject constructor(private val clase: ClaseRepositorio) {
    fun invoke(id: Int) = clase.deleteClase(id)
}