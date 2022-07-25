package com.example.autoescuelapp.data.modelo


import com.google.gson.annotations.SerializedName

data class CocheData(
    @SerializedName("dniAdmin")
    val dniAdmin: String,
    @SerializedName("fechaCompra")
    val fechaCompra: String,
    @SerializedName("fechaCreacion")
    val fechaCreacion: String,
    @SerializedName("marca")
    val marca: String,
    @SerializedName("matricula")
    val matricula: String,
    @SerializedName("modelo")
    val modelo: String,
    @SerializedName("proximaItv")
    val proximaItv: String
)