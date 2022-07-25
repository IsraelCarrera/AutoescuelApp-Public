package com.example.autoescuelapp.data.modelo


import com.google.gson.annotations.SerializedName

data class FeedbackData(
    @SerializedName("cuerpo")
    val cuerpo: String,
    @SerializedName("dniAlumno")
    val dniAlumno: String,
    @SerializedName("idClase")
    val idClase: Int,
    @SerializedName("puntuacion")
    val puntuacion: Int,
    @SerializedName("titulo")
    val titulo: String
)