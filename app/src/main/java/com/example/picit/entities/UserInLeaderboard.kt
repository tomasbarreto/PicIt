package com.example.picit.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserInLeaderboard(
    val userId: String = "",
    val userName: String = "",
    val points:Int = 0,
    val didSeeWinnerScreen: Boolean = false,
    val winStreak: Int = 0,
)
