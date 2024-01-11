package com.example.picit.entities

import kotlinx.serialization.Serializable

@Serializable
data class RePicRoom(
    var id: String? = null,
    val name:String = "",
    val gameType: GameType = GameType.REPIC,
    val image: String = "", // TODO: mudar para representar imagem
    val maxCapacity: Int = 0,
    val currentCapacity: Int = 0,
    val maxNumOfChallenges: Int = 0,
    val currentNumOfChallengesDone: Int = 0,
    val winnerAnnouncementTime: Time = Time(),
    val photoSubmissionOpeningTime: Time = Time(),
    val photoSubmissionClosingTime: Time = Time(),
    val leaderboard: List<UserInLeaderboard> = emptyList(),
    val picturesSubmitted: List<RePicPhoto> = emptyList(),
    val pictureReleaseTime: Time = Time(),

    val privacy: Boolean = false,
    val privacyCode: String = ""
)