package com.example.picit.entities

data class JoinRoomRequest(
    val senderName: String = "", // id of the user that sent the request
    val roomId: String = "", // id of the room to join
    val gameType: GameType = GameType.REPIC
)
