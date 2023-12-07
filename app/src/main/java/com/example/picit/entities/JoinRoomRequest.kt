package com.example.picit.entities

data class JoinRoomRequest(
    val userIdThatSentRequest: Int, // id of the user that sent the request
    val roomId: Int, // id of the room to join

)
