package com.example.autoescuelapp.usescases.coche

import com.example.autoescuelapp.data.repositorio.CocheRepositorio
import javax.inject.Inject

class GetCocheByMatriculaUC @Inject constructor(private val coche: CocheRepositorio) {
    fun invoke(matricula: String) = coche.getCocheByMatricula(matricula)
}