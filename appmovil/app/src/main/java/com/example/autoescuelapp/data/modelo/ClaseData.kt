package com.example.autoescuelapp.data.modelo


import com.google.gson.annotations.SerializedName

data class ClaseData(
    @SerializedName("dniAlumno")
    val dniAlumno: String,
    @SerializedName("dniProfesor")
    val dniProfesor: String,
    @SerializedName("duracion")
    val duracion: String,
    @SerializedName("horarioInicio")
    val horarioInicio: String,
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("id")
    val id: Int
)