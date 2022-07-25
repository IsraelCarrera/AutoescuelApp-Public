package com.example.autoescuelapp.data.sources.remote

import com.example.autoescuelapp.data.modelo.FeedbackData
import com.example.autoescuelapp.data.modelo.InfoData
import retrofit2.Response
import retrofit2.http.*

interface FeedbackAPI {
    @GET("feedback/get/idClase")
    suspend fun getFeedback(
        @Query("idClase") idClase: Int,
    ): Response<FeedbackData>

    @POST("feedback/registrar")
    suspend fun addFeedback(
        @Body feedback: FeedbackData,
    ): Response<Unit>

    @DELETE("feedback/borrar")
    suspend fun deleteFeedback(
        @Query("idClase") idClase: Int,
    ): Response<InfoData>

    @PUT("feedback/modificar")
    suspend fun updateFeedback(
        @Body feedback: FeedbackData,
    ): Response<InfoData>
}