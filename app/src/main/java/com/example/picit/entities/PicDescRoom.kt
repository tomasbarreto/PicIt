package com.example.picit.entities

import java.util.Date

data class PicDescRoom(
    var id: String? = null,
    val name:String = "",
    val gameType: GameType = GameType.PICDESC,
    val maxCapacity: Int = 0,
    val currentCapacity: Int = 0,
    val maxNumOfChallenges: Int = 0,
    val currentNumOfChallengesDone: Int = 0,
    val winnerAnnouncementTime: String = "",
    val photoSubmissionOpeningTime: String = "",
    val photoSubmissionClosingTime: String = "",
    val leaderboard: List<UserInLeaderboard> = emptyList(),

    val photosSubmitted: List<PicDescPhoto> = emptyList(),
    val descriptionSubmissionOpeningTime : String = "",
    val descriptionSubmissionClosingTime: String = "",
    val currentLeader: String ="",// user id

    val privacy: Boolean = false,
    val privacyCode: String = ""
)
