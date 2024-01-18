package com.example.picit.network

import kotlinx.serialization.Serializable

@Serializable
data class RequestModel(
    val text_prompts:List<TextPrompt> = emptyList()
)