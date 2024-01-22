package com.example.picit.entities


data class RePicRoom(
    var id: String? = null,
    val name:String = "",
    val gameType: GameType = GameType.REPIC,
    // Keeps all generated photos for all daily challenges
    val generatedImagesUrls: List<String> = emptyList(),
    // keeps all submitted photos for all daily challenges
    val photosSubmitted: List<List<RePicPhoto>> = emptyList(),
    // where the winners are saved
    val winners: List<RePicPhoto> = emptyList(),
    val maxCapacity: Int = 0,
    val currentCapacity: Int = 0,
    val maxNumOfChallenges: Int = 0,
    val currentNumOfChallengesDone: Int = 0,
    val winnerAnnouncementTime: Time = Time(),
    val leaderboard: List<UserInLeaderboard> = emptyList(),
//    val picturesSubmitted: List<RePicPhoto> = emptyList(),
    val pictureReleaseTime: Time = Time(),

    val privacy: Boolean = false,
    val privacyCode: String = ""
)