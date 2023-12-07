package com.example.picit.entities

data class User(
    val id: Int,
    val name:String,
    val email:String,
    val password:String,
    val rooms: List<Int>, // id das salas
    val friends: List<Int>, // id dos users
    val requestsToJoin : List<JoinRoomRequest>,
    val friendRequests: List<Int>, // id dos users
    val maxPoints: Int,
    val totalWins: Int,
    val maxWinStreak: Int,
    val nPhotosTaken: Int
)
