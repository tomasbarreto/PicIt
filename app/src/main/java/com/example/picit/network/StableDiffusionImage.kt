package com.example.picit.network

import kotlinx.serialization.Serializable

@Serializable
data class StableDiffusionImage(
    val artifacts: List<Artifacts> = emptyList()
)

@Serializable
data class Artifacts(
    val base64:String = "",
    val seed: Long = 0,
    val finishReason:String = ""
)