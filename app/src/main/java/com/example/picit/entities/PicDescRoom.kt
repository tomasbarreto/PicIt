package com.example.picit.entities

import java.util.Date

data class PicDescRoom(
    val id: Int,
    val name:String,
    val gameType: GameType,
    val maxCapacity: Int,
    val currentCapacity: Int,
    val maxNumOfChallenges: Int,
    val currentNumOfChallengesDone: Int,
    val winnerAnnouncementTime: Date,
    val photoSubmissionOpeningTime: Date,
    val photoSubmissionClosingTime: Date,
    val leaderboard: List<UserInLeaderboard>,

    val photosSubmitted: List<PicDescPhoto>,
    val descriptionSubmissionOpeningTime : Date,
    val descriptionSubmissionClosingTime: Date,
    val currentLeader: Int // user id
)
