package com.example.autoescuelapp.usescases.persona

import com.example.autoescuelapp.data.repositorio.PersonaRepositorio
import javax.inject.Inject

class CambiarPassUC @Inject constructor(private val personas: PersonaRepositorio) {
    fun invoke(dni: String, passVieja: String, passNueva: String) =
        personas.cambiarPass(dni, passVieja, passNueva)
}