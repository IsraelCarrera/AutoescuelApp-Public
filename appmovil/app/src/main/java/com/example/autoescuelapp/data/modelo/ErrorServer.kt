package com.example.autoescuelapp.data.modelo


import com.google.gson.annotations.SerializedName

data class ErrorServer(
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("mensaje")
    val mensaje: String
)