package com.example.autoescuelapp.data.modelo


import com.google.gson.annotations.SerializedName

data class ProfesoresPocaInfoData(
    @SerializedName("apellidos")
    val apellidos: String,
    @SerializedName("aprobado")
    val aprobado: Boolean,
    @SerializedName("dni")
    val dni: String,
    @SerializedName("nombre")
    val nombre: String
)