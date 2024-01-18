package com.example.picit.network

import kotlinx.serialization.Serializable


@Serializable
data class TextPrompt(val text:String="", val weight: Double= 0.0)
