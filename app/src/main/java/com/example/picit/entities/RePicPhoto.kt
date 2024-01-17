package com.example.picit.entities

data class RePicPhoto(
    val photoUrl: String = "",
    val userId: String = "",
    val username: String = "",
    val submissionTime: Time = Time(),
    val location: String = "location"
)
