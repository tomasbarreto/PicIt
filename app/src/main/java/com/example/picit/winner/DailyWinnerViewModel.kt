package com.example.picit.winner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicPhoto
import com.example.picit.entities.User
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class DailyWinnerViewModel: ViewModel() {

    var shownRepoicWinnerPhoto: RePicPhoto by mutableStateOf(RePicPhoto())
    var shownPicDescWinnerPhoto: PicDescPhoto by mutableStateOf(PicDescPhoto())
    var screenTitle: String by mutableStateOf("")
    var picDescDescription: String by mutableStateOf("")
    private lateinit var award: Award
    private lateinit var fastestWinnerPhoto: PicDescPhoto
    private lateinit var mostVotedWinnerPhoto: PicDescPhoto

    fun setFastestWinnerPhoto(winnerPhoto: PicDescPhoto) {
        this.fastestWinnerPhoto = winnerPhoto
    }

    fun setMostVotedWinnerPhoto(winnerPhoto: PicDescPhoto) {
        this.mostVotedWinnerPhoto = winnerPhoto
    }

    fun setPicDescDesc(description: String) {
        this.picDescDescription = description
    }

    fun setRePicWinnerPhoto(winnerPhoto: RePicPhoto) {
        this.shownRepoicWinnerPhoto = winnerPhoto
    }

    fun setCurrentAward(award: Award) {
        this.award = award

        shownPicDescWinnerPhoto = if (this.award == Award.FASTEST) {
            this.fastestWinnerPhoto
        } else {
            this.mostVotedWinnerPhoto
        }

        screenTitle = if (this.award == Award.FASTEST) {
            "Fastest Award"
        } else if (this.award == Award.MOST_VOTED) {
            "Most Voted Award"
        }
        else {
            "WINNER"
        }
    }

    fun getCurrentAward(): Award {
        return this.award
    }

    fun setUserWinnerScreenVisibility(currentPicDescRoom: PicDescRoom, userID: String, visibility: Boolean) {
        val database = Firebase.database

        val currentLeaderboard = currentPicDescRoom.leaderboard
        val updatedLeaderboard = mutableListOf<UserInLeaderboard>()

        for (user in currentLeaderboard) {
            if (user.userId == userID) {
                updatedLeaderboard.add(user.copy(didSeeWinnerScreen = visibility))
            }
            else {
                updatedLeaderboard.add(user)
            }
        }

        val updatedPicDescRoom = currentPicDescRoom.copy(leaderboard = updatedLeaderboard)

        val roomRef = database.getReference("picDescRooms/${currentPicDescRoom.id}")
        roomRef.setValue(updatedPicDescRoom)
    }

    fun incrementPlayersScores(currentPicDescRoom: PicDescRoom) {
        val database = Firebase.database

        val currentLeaderboard = currentPicDescRoom.leaderboard
        val updatedLeaderboard = mutableListOf<UserInLeaderboard>()

        for (user in currentLeaderboard) {
            if (user.userId == fastestWinnerPhoto.userId || user.userId == mostVotedWinnerPhoto.userId) {
                val newPoints = user.points + 1
                val newStreak = user.winStreak + 1
                updatedLeaderboard.add(user.copy(points = newPoints, winStreak = newStreak))
                updateUserAchievements(user.userId,newPoints,newStreak)
            }
            else {
                updatedLeaderboard.add(user.copy(winStreak = 0))
            }
        }

        val updatedPicDescRoom = currentPicDescRoom.copy(leaderboard = updatedLeaderboard)

        val roomRef = database.getReference("picDescRooms/${currentPicDescRoom.id}")
        roomRef.setValue(updatedPicDescRoom)
    }

    private fun updateUserAchievements(userId: String, newPoints: Int, newStreak: Int) {
        val db = Firebase.database
        val userRef = db.getReference("users/$userId")
        userRef.get().addOnSuccessListener {
            val user = it.getValue<User>()!!

            val updatedMaxPoints = if ( newPoints > user.maxPoints) newPoints else newPoints
            val updatedMaxStreak = if ( newStreak > user.maxWinStreak) newStreak else newStreak
            val updatedUser = user.copy(maxPoints = updatedMaxPoints, maxWinStreak = updatedMaxStreak)

            userRef.setValue(user)
        }
    }

    fun incrementDailyChallenges(currentPicDescRoom: PicDescRoom) {
        val database = Firebase.database

        val updatedPicDescRoom = currentPicDescRoom.copy(currentNumOfChallengesDone = currentPicDescRoom.currentNumOfChallengesDone + 1)

        val roomRef = database.getReference("picDescRooms/${currentPicDescRoom.id}")
        roomRef.setValue(updatedPicDescRoom)
    }
}
