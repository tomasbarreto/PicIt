package com.example.picit.createroom.repic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RepicRoomTimeSettingsViewModel: ViewModel() {

    fun registerRepicRoom(
        roomName: String,
        roomCapacity: String,
        roomNumChallenges: String,
        privacy: Boolean,
        privacyCode: String,
        timePictureRelease: String,
        timePictureSubmissionStart:String,
        timePictureSubmissionEnd:String,
        timeWinner:String,
        onClickGoHomeScreen: ()->Unit = {},
        currentUserRooms: List<String>,
        currentUserId: String
    ) {

        val newRepicRoom = RePicRoom(name = roomName, currentCapacity = 1, maxCapacity = roomCapacity.toInt(), maxNumOfChallenges = roomNumChallenges.toInt(),
            winnerAnnouncementTime = timeWinner, photoSubmissionOpeningTime = timePictureSubmissionStart,
            photoSubmissionClosingTime = timePictureSubmissionEnd, pictureReleaseTime = timePictureRelease, privacy = privacy, privacyCode = privacyCode)

        val database = Firebase.database
        val roomRef = database.getReference("repicRooms").push()
        roomRef.setValue(newRepicRoom)

        updateUserRooms(currentUserRooms, currentUserId, roomRef.key.toString())
        onClickGoHomeScreen()
    }

    private fun updateUserRooms(currentUserRooms: List<String>, currentUserId: String, roomId: String) {
        val database = Firebase.database

        var userCurrentRooms = mutableStateOf(currentUserRooms)
        userCurrentRooms.value = userCurrentRooms.value + roomId

        val roomsRef = database.getReference("users/" + currentUserId + "/repicRooms")
        roomsRef.setValue(userCurrentRooms.value)
    }

}