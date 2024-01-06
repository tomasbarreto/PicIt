package com.example.picit.createroom.picdesc

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.Time
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.database

class PicDescTimeSettingsViewModel: ViewModel() {

    fun registerPicDescRoom(
        roomName: String,
        roomCapacity: String,
        roomNumChallenges: String,
        privacy: Boolean,
        privacyCode: String,
        timeDescSubmissionStart: Time,
        timeDescSubmissionEnd: Time,
        timePictureSubmissionStart:Time,
        timePictureSubmissionEnd: Time,
        timeWinner:Time,
        onClickGoHomeScreen: ()->Unit = {},
        currentUserRooms: List<String>,
        currentUserId: String
    ) {
        val database = Firebase.database
        val roomRef = database.getReference("picDescRooms").push()

        val newPicDescRoom = PicDescRoom(id = roomRef.key,name = roomName, currentCapacity = 1,
            maxCapacity = roomCapacity.toInt(), maxNumOfChallenges = roomNumChallenges.toInt(),
            winnerAnnouncementTime = timeWinner, photoSubmissionOpeningTime = timePictureSubmissionStart,
            photoSubmissionClosingTime = timePictureSubmissionEnd, descriptionSubmissionOpeningTime = timeDescSubmissionStart,
            descriptionSubmissionClosingTime = timeDescSubmissionEnd, currentLeader = currentUserId,
            privacy = privacy, privacyCode = privacyCode, leaderboard = listOf(UserInLeaderboard(currentUserId,0))
        )
        roomRef.setValue(newPicDescRoom)


        updateUserRooms(currentUserRooms, currentUserId, roomRef.key.toString())
        onClickGoHomeScreen()
    }

    private fun updateUserRooms(currentUserRooms: List<String>, currentUserId: String, roomId: String) {
        val database = Firebase.database

        var userCurrentRooms = mutableStateOf(currentUserRooms)
        userCurrentRooms.value = userCurrentRooms.value + roomId

        val roomsRef = database.getReference("users/" + currentUserId + "/picDescRooms")
        roomsRef.setValue(userCurrentRooms.value)
    }

}