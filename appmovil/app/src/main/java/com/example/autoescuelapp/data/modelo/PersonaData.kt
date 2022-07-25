package com.example.autoescuelapp.data.modelo


import com.google.gson.annotations.SerializedName

data class PersonaData(
    @SerializedName("apellidos")
    val apellidos: String?,
    @SerializedName("aprobado")
    val aprobado: Boolean?,
    @SerializedName("correo")
    val correo: String?,
    @SerializedName("direccion")
    val direccion: String?,
    @SerializedName("dni")
    val dni: String?,
    @SerializedName("dniTutor")
    val dniTutor: String?,
    @SerializedName("matriculaCoche")
    val matriculaCoche: String?,
    @SerializedName("nombre")
    val nombre: String?,
    @SerializedName("telefono")
    val telefono: String?,
    @SerializedName("tipoPersona")
    val tipoPersona: String?
)