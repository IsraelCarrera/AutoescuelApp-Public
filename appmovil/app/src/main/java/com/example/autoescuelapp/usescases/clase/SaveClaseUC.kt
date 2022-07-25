package com.example.autoescuelapp.usescases.clase

import com.example.autoescuelapp.data.repositorio.ClaseRepositorio
import com.example.autoescuelapp.domain.Clase
import javax.inject.Inject

class SaveClaseUC @Inject constructor(private val clase: ClaseRepositorio) {
    fun invoke(claseRegistrar: Clase) = clase.saveClase(claseRegistrar)
}