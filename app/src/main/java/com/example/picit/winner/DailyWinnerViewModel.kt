package com.example.picit.winner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.UserInLeaderboard
import com.example.picit.picdesc.Award
import com.google.firebase.Firebase
import com.google.firebase.database.database

class DailyWinnerViewModel: ViewModel() {

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

    fun setCurrentAward(award: Award) {
        this.award = award

        shownPicDescWinnerPhoto = if (this.award == Award.FASTEST) {
            this.fastestWinnerPhoto
        } else {
            this.mostVotedWinnerPhoto
        }

        screenTitle = if (this.award == Award.FASTEST) {
            "Fastest Award"
        } else {
            "Most Voted Award"
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
                updatedLeaderboard.add(user.copy(points = user.points + 1))
            }
            else {
                updatedLeaderboard.add(user)
            }
        }

        val updatedPicDescRoom = currentPicDescRoom.copy(leaderboard = updatedLeaderboard)

        val roomRef = database.getReference("picDescRooms/${currentPicDescRoom.id}")
        roomRef.setValue(updatedPicDescRoom)
    }

    fun incrementDailyChallenges(currentPicDescRoom: PicDescRoom) {
        val database = Firebase.database

        val updatedPicDescRoom = currentPicDescRoom.copy(currentNumOfChallengesDone = currentPicDescRoom.currentNumOfChallengesDone + 1)

        val roomRef = database.getReference("picDescRooms/${currentPicDescRoom.id}")
        roomRef.setValue(updatedPicDescRoom)
    }
}
