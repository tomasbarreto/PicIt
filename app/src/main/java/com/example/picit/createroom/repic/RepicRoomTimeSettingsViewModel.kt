package com.example.picit.createroom.repic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RepicRoomTimeSettingsViewModel: ViewModel() {

    fun registerRepicRoom(
        roomName: String,
        roomCapacity: String,
        roomNumChallenges: String,
        timePictureRelease: String,
        timePictureSubmissionStart:String,
        timePictureSubmissionEnd:String,
        timeWinner:String,
        onClickGoHomeScreen: ()->Unit = {},
        currentUser: User,
        currentUserId: String
    ) {

        val newRepicRoom = RePicRoom(name = roomName, maxCapacity = roomCapacity.toInt(), maxNumOfChallenges = roomNumChallenges.toInt(),
            winnerAnnouncementTime = timeWinner, photoSubmissionOpeningTime = timePictureSubmissionStart,
            photoSubmissionClosingTime = timePictureSubmissionEnd, pictureReleaseTime = timePictureRelease)

        val database = Firebase.database
        val repicRoomRef = database.getReference("repicRooms").push()
        repicRoomRef.setValue(newRepicRoom)

        updateUserRooms(currentUser, currentUserId, repicRoomRef.key.toString())
        onClickGoHomeScreen()
    }

    private fun updateUserRooms(currentUser: User, currentUserId: String, roomId: String) {
        val database = Firebase.database

        var userCurrentRooms = mutableStateOf(currentUser.rooms)
        userCurrentRooms.value = userCurrentRooms.value + roomId

        val roomsRef = database.getReference("users/" + currentUserId + "/rooms")
        roomsRef.setValue(userCurrentRooms.value)
    }

}