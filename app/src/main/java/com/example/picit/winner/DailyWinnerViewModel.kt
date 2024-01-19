package com.example.picit.winner

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicPhoto
import com.example.picit.entities.User
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

private val TAG = "DailyWinnerViewModel"
class DailyWinnerViewModel: ViewModel() {
    fun findFastestValidPhoto(photos: List<PicDescPhoto>) : PicDescPhoto {
        var res = photos[0]

        for (photo in photos){
            if (photo.submissionTime.hours < res.submissionTime.hours ||
                (photo.submissionTime.hours == res.submissionTime.hours &&
                        photo.submissionTime.minutes < res.submissionTime.minutes) ){
                res = photo
            }
        }

        return res
    }

    fun findBestRatedPhoto(photos: List<PicDescPhoto>): PicDescPhoto {
        var res = photos[0]

        for(photo in photos){
            val photoRating = getPhotoRating(photo)

            val winnerRating = getPhotoRating(res)
            res = if (photoRating > winnerRating) photo else res
        }

        return res

    }

    fun awardUser(userId: String, room: PicDescRoom, points:Int =1, callback: ()->Unit = {}) {
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        var userInLeaderboard = UserInLeaderboard()
        val updatedLeaderboard = room.leaderboard.toMutableList()

        for(user in updatedLeaderboard){
            if(user.userId == userId){
                userInLeaderboard = user

            }
        }
        updatedLeaderboard.remove(userInLeaderboard)

        val updatedPoints = userInLeaderboard.points+points
        val updatedStreak = userInLeaderboard.winStreak+points
        val updatedUserInLeaderboard = userInLeaderboard.copy(points = updatedPoints, winStreak = updatedStreak)
        updatedLeaderboard.add(updatedUserInLeaderboard)

        val updatedRoom = room.copy(leaderboard = updatedLeaderboard)
        roomRef.setValue(updatedRoom)
        Log.d(TAG, "room udpated! $updatedRoom ")

        //Update user achievements
        val userRef = db.getReference("users/$userId")
        userRef.get().addOnSuccessListener {
            val savedUser = it.getValue<User>()!!
            val currentMaxPoints = savedUser.maxPoints
            val currentMaxWinStreak = savedUser.maxWinStreak

            val updatedMaxPoints = if (updatedPoints > currentMaxPoints) updatedPoints else currentMaxPoints
            val updatedMaxWinStreak = if(updatedStreak > currentMaxWinStreak) updatedStreak else currentMaxWinStreak

            val updatedUser = savedUser.copy(maxPoints = updatedMaxPoints, maxWinStreak = updatedMaxWinStreak)
            userRef.setValue(updatedUser)
            callback()

        }
    }

    fun getPhotoRating(photo: PicDescPhoto): Double{
        return photo.ratingSum / (photo.usersThatVoted.size -1.0)
    }

    fun leaveAwardScreen(room: PicDescRoom, user:User) {
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")


    }

    fun userSawWinnerScreen(userId: String, room: PicDescRoom) {
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        var userInLeaderboard = UserInLeaderboard()
        val updatedLeaderboard = room.leaderboard.toMutableList()
        for(user in updatedLeaderboard){
            if(user.userId == userId){
                userInLeaderboard = user
            }
        }
        updatedLeaderboard.remove(userInLeaderboard)

        val updateUserInLeaderboard = userInLeaderboard.copy(didSeeWinnerScreen = true)
        updatedLeaderboard.add(updateUserInLeaderboard)

        val updatedRoom = room.copy(leaderboard = updatedLeaderboard)
        roomRef.setValue(updatedRoom)

    }


}
