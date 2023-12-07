package com.example.picit.entities

import java.util.Date

data class RePicRoom(
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

    val picturesSubmitted: List<RePicPhoto>,
    val pictureReleaseTime: Date,
)