package com.example.autoescuelapp.usescases.persona

import com.example.autoescuelapp.data.repositorio.PersonaRepositorio
import javax.inject.Inject

class GetPersonaByDniUC @Inject constructor(private val personas: PersonaRepositorio) {
    fun invoke(dni: String) = personas.getPersonaByDni(dni)
}