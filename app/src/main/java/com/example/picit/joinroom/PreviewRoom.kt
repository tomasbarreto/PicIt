package com.example.picit.joinroom

data class RoomPreviewInfo(
    val name:String = "",
    val maxSize: Int = 0,
    val currentSize: Int = 0,
    val gameType: String,
    val maxDailyChallenges: Int = 0,
    val dailyChallengesDone: Int = 0,
)
