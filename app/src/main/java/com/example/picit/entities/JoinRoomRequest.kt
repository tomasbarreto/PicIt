package com.example.picit.entities

import kotlinx.serialization.Serializable

@Serializable
data class JoinRoomRequest(
    val userIdThatSentRequest: String = "", // id of the user that sent the request
    val roomId: String = "", // id of the room to join
    val gameType: GameType = GameType.REPIC
)
