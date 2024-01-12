package com.example.picit.entities

import kotlinx.serialization.Serializable

@Serializable
data class Time (
    val hours: Int = 0,
    val minutes: Int = 0,
    val second: Int = 0
)