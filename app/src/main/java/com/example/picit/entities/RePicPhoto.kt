package com.example.picit.entities

import kotlinx.serialization.Serializable

@Serializable
data class RePicPhoto(
    val photoUrl: String = "",
    val userId: String = "",
    val username: String = ""
)
