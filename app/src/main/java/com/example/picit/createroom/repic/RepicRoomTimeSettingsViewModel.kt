package com.example.picit.createroom.repic

import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.util.Calendar

class RepicRoomTimeSettingsViewModel: ViewModel() {

    fun registerRepicRoom(
        roomName: String,
        roomCapacity: String,
        roomNumChallenges: String,
        hoursPictureRelease: String,
        minutesPictureRelease: String,
        hoursPictureSubmissionStart:String,
        minutesPictureSubmissionStart:String,
        hoursPictureSubmissionEnd:String,
        minutesPictureSubmissionEnd:String,
        hoursWinner:String,
        minutesWinner:String
    ) {

        var timePictureRealease = Calendar.getInstance()
        timePictureRealease.set(Calendar.HOUR_OF_DAY, hoursPictureRelease.toInt())
        timePictureRealease.set(Calendar.MINUTE, minutesPictureRelease.toInt())

        var timePictureSubmissionStart = Calendar.getInstance()
        timePictureSubmissionStart.set(Calendar.HOUR_OF_DAY, hoursPictureSubmissionStart.toInt())
        timePictureSubmissionStart.set(Calendar.MINUTE, minutesPictureSubmissionStart.toInt())

        var timePictureSubmissionEnd = Calendar.getInstance()
        timePictureSubmissionEnd.set(Calendar.HOUR_OF_DAY, hoursPictureSubmissionEnd.toInt())
        timePictureSubmissionEnd.set(Calendar.MINUTE, minutesPictureSubmissionEnd.toInt())

        var timeWinner = Calendar.getInstance()
        timeWinner.set(Calendar.HOUR_OF_DAY, hoursWinner.toInt())
        timeWinner.set(Calendar.MINUTE, minutesWinner.toInt())

        val newRepicRoom = RePicRoom(name = roomName, maxCapacity = roomCapacity.toInt(), maxNumOfChallenges = roomNumChallenges.toInt(),
            winnerAnnouncementTime = timeWinner, photoSubmissionOpeningTime = timePictureSubmissionStart,
            photoSubmissionClosingTime = timePictureSubmissionEnd, pictureReleaseTime = timePictureRealease)

        val database = Firebase.database
        database.getReference("repicRooms").push().setValue(newRepicRoom)
    }

}