package com.example.picit.entities

import kotlinx.serialization.Serializable

@Serializable
data class RePicRoom(
    var id: String? = null,
    val name:String = "",
    val gameType: GameType = GameType.REPIC,
    val maxCapacity: Int = 0,
    val currentCapacity: Int = 0,
    val maxNumOfChallenges: Int = 0,
    val currentNumOfChallengesDone: Int = 0,
    val winnerAnnouncementTime: String = "",
    val photoSubmissionOpeningTime: String = "",
    val photoSubmissionClosingTime: String = "",
    val leaderboard: List<UserInLeaderboard> = emptyList(),
    val picturesSubmitted: List<RePicPhoto> = emptyList(),
    val pictureReleaseTime: String = "",

    val privacy: Boolean = false,
    val privacyCode: String = ""
)