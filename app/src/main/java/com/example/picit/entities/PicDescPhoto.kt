package com.example.picit.entities

data class PicDescPhoto(
    val photoUrl: String = "",// TODO: ver como representar a photo
    val userId: String = "",
    val username: String = "user name",
    val location: String = "location",
    val submissionTime: Time = Time(),
    val usersThatVoted: List<String> = emptyList(), // ids de users que votaram
    val leaderVote: Boolean = false,
    val averageRating: Double = 0.0
)
