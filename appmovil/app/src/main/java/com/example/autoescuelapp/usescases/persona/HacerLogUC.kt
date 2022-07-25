package com.example.autoescuelapp.usescases.persona

import com.example.autoescuelapp.data.repositorio.PersonaRepositorio
import com.example.autoescuelapp.domain.PersonaLogin
import javax.inject.Inject

class HacerLogUC @Inject constructor(private val personas: PersonaRepositorio) {
    fun invoke(pl: PersonaLogin) = personas.hacerLog(pl)
}