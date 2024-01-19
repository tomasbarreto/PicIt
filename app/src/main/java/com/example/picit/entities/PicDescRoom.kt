package com.example.picit.entities

data class PicDescRoom(
    var id: String? = null,
    val name:String = "",
    val gameType: GameType = GameType.PICDESC,
    val photoDescription: String = "",
    val maxCapacity: Int = 0,
    val currentCapacity: Int = 0,
    val maxNumOfChallenges: Int = 0,
    val currentNumOfChallengesDone: Int = 0,
    val winnerAnnouncementTime: Time = Time(),
    val photoSubmissionOpeningTime: Time = Time(),
    //val photoSubmissionClosingTime: Time = Time(), // winnerAnnouncement
    val leaderboard: List<UserInLeaderboard> = emptyList(),

    val photosSubmitted: List<PicDescPhoto> = emptyList(),
    val descriptionSubmissionOpeningTime : Time = Time(),
    //val descriptionSubmissionClosingTime: Time = Time(), // photoSubmissionOpening
    val currentLeader: String = "", // user id

    val privacy: Boolean = false,
    val privacyCode: String = "",
)
