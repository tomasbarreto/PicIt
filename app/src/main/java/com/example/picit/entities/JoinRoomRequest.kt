package com.example.picit.entities

import kotlinx.serialization.Serializable

@Serializable
data class JoinRoomRequest(
    val usernameThatSentRequest: String = "", // id of the user that sent the request
    val roomId: String = "", // id of the room to join
    val roomName: String = "", // name of the room to join
    val gameType: GameType = GameType.REPIC
)
