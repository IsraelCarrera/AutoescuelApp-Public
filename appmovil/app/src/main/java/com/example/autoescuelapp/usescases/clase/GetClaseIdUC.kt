package com.example.autoescuelapp.usescases.clase

import com.example.autoescuelapp.data.repositorio.ClaseRepositorio
import javax.inject.Inject

class GetClaseIdUC @Inject constructor(private val clase: ClaseRepositorio) {
    fun invoke(id: Int) = clase.getClaseId(id)

}