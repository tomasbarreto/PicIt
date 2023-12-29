package com.example.picit.entities

import kotlinx.serialization.Serializable
import java.util.Calendar

@Serializable
data class RePicRoom(
//    val id: Int,
    val name:String = "",
    val gameType: GameType = GameType.REPIC,
    val maxCapacity: Int = 0,
    val currentCapacity: Int = 0,
    val maxNumOfChallenges: Int = 0,
    val currentNumOfChallengesDone: Int = 0,
    val winnerAnnouncementTime: Calendar? = null,
    val photoSubmissionOpeningTime: Calendar? = null,
    val photoSubmissionClosingTime: Calendar? = null,
    val leaderboard: List<UserInLeaderboard> = emptyList(),
    val picturesSubmitted: List<RePicPhoto> = emptyList(),
    val pictureReleaseTime: Calendar? = null,
)