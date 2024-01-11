package com.example.picit.entities

data class PicDescPhoto(
    val photo: String ="",// TODO: ver como representar a photo
    val userId: String = "",
    val submissionTime: Time = Time(),
    val usersThatVoted: List<String> = emptyList(), // ids de users que votaram
    val leaderVote: Boolean = false,
    val averageRating: Double = 0.0
)
