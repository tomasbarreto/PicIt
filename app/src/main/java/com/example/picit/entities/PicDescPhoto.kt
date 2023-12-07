package com.example.picit.entities

data class PicDescPhoto(
    val photo: Int,// TODO: ver como representar a photo

    val usersThatVoted: List<Int>, // ids de users que votaram
    val leaderVote: Boolean,
    val averageRating: Double
)
