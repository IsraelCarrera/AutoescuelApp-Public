package com.example.autoescuelapp.data.modelo


import com.google.gson.annotations.SerializedName

data class IncidenciaData(
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("dniProfesor")
    val dniProfesor: String,
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("matricula")
    val matriculaCoche: String,
    @SerializedName("titulo")
    val tituloIncidencia: String,
    @SerializedName("ubicacion")
    val ubicacion: String
)