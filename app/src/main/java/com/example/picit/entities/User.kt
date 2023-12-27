package com.example.picit.entities

import kotlinx.serialization.Serializable

@Serializable
data class User (
    //val uid: String,
    val username:String = "",
    val name:String = "",
    val rooms: List<Int> = emptyList(), // id das salas
    val friends: List<Int> = emptyList(), // id dos users
    val requestsToJoin : List<JoinRoomRequest> = emptyList(),
    val friendRequests: List<Int> = emptyList(), // id dos users
    val maxPoints: Int = 0,
    val totalWins: Int = 0,
    val maxWinStreak: Int = 0,
    val nrPhotosTaken: Int = 0
)
