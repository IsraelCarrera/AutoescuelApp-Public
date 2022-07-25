package com.example.autoescuelapp.usescases.incidencia

import com.example.autoescuelapp.data.repositorio.IncidenciaRepositorio
import com.example.autoescuelapp.domain.Incidencia
import javax.inject.Inject

class PostIncidenciaUC @Inject constructor(private val incidenciaRepositorio: IncidenciaRepositorio) {
    fun invoke(incidencia: Incidencia) = incidenciaRepositorio.postIncidencia(incidencia)
}