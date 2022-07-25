package com.example.autoescuelapp.usescases.persona

import com.example.autoescuelapp.data.repositorio.PersonaRepositorio
import javax.inject.Inject

class GetProfesorByIdClaseUC @Inject constructor(private val personas: PersonaRepositorio) {
    fun invoke(idClase: Int) = personas.getProfesorByIdClase(idClase)
}