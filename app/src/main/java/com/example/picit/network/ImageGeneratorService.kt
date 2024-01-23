package com.example.picit.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL =
    "https://api.stability.ai/v1/generation/stable-diffusion-xl-1024-v1-0/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface StableDiffusionService {
    @Headers(
        "Authorization:sk-hDARwxmYNq1G2kGsIi7JZtHCpEZyJQGA7TILLptRLyJuS6oA",
        "Content-Type:application/json"
    )
    @POST("text-to-image")
    suspend fun getGeneratedImage(@Body body: RequestModel): StableDiffusionImage
}

object StableDiffusionApi {
    val retrofitService : StableDiffusionService by lazy {
        retrofit.create(StableDiffusionService::class.java)
    }
}






