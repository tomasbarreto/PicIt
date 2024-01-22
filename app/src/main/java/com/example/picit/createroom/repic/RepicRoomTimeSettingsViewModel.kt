package com.example.picit.createroom.repic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.Time
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.util.Calendar

class RepicRoomTimeSettingsViewModel: ViewModel() {

    fun registerRepicRoom(
        roomName: String,
        roomCapacity: String,
        roomNumChallenges: String,
        privacy: Boolean,
        privacyCode: String,
        timePictureRelease: Time,
        timeWinner:Time,
        onClickGoHomeScreen: ()->Unit = {},
        currentUserRooms: List<String>,
        currentUserId: String,
        currentUserName: String
    ) {
        val database = Firebase.database
        val roomRef = database.getReference("repicRooms").push()

        val newRepicRoom = RePicRoom(id=roomRef.key,name = roomName, currentCapacity = 1, maxCapacity = roomCapacity.toInt(),
            maxNumOfChallenges = roomNumChallenges.toInt(), leaderboard = listOf(UserInLeaderboard(currentUserId,currentUserName, 0)),
            winnerAnnouncementTime = timeWinner, pictureReleaseTime = timePictureRelease,
            privacy = privacy, privacyCode = privacyCode)

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

    fun validTimes(time: Time, time1: Time): Boolean {
        val currentCalendar = Calendar.getInstance()
        val currentTime = Time(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(
            Calendar.MINUTE))

        return timeIsStrictlyGreater(time1,time) && timeIsInInterval(currentTime, time, time1)
    }

    private fun timeIsInInterval(currentTime: Time, startTime: Time, endTime: Time): Boolean {
        return ((startTime.hours<currentTime.hours && currentTime.hours<endTime.hours) ||

                (startTime.hours==currentTime.hours && currentTime.hours==endTime.hours &&
                        startTime.minutes<currentTime.minutes && currentTime.minutes < endTime.minutes) ||

                (startTime.hours == currentTime.hours && startTime.minutes<currentTime.minutes && currentTime.hours < endTime.hours)||

                (currentTime.hours == endTime.hours && currentTime.minutes < endTime.minutes && startTime.hours < currentTime.hours))
    }

    private fun timeIsStrictlyGreater(time: Time, time1: Time): Boolean {
        return time.hours>time1.hours ||
                (time.hours == time1.hours && time.minutes > time1.minutes)
    }

}