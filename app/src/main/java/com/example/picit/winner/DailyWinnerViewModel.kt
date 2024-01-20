package com.example.picit.winner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicPhoto
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.User
import com.example.picit.entities.UserInLeaderboard
import com.example.picit.repic.PhotoComparator
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.launch

private val TAG = "DailyWinnerViewModel"
class DailyWinnerViewModel: ViewModel() {
    fun findFastestValidPhoto(photos: List<PicDescPhoto>) : PicDescPhoto {
        var res = PicDescPhoto()

        for (photo in photos){
            if (!res.leaderVote||
                (photo.submissionTime.hours < res.submissionTime.hours ||
                (photo.submissionTime.hours == res.submissionTime.hours &&
                        photo.submissionTime.minutes < res.submissionTime.minutes)) ){
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
            res = if (photo.leaderVote && photoRating > winnerRating) photo else res
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
        roomRef.setValue(updatedRoom).addOnSuccessListener {
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
                userRef.setValue(updatedUser).addOnSuccessListener {
                    callback()
                }
            }
        }
    }


    fun awardUser(userId: String, room: RePicRoom, points:Int =1, callback: ()->Unit = {}) {
        val db = Firebase.database
        val roomRef = db.getReference("repicRooms/${room.id}")

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
        roomRef.setValue(updatedRoom).addOnSuccessListener {
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
                userRef.setValue(updatedUser).addOnSuccessListener {
                    callback()
                }
            }
        }
    }

    fun getPhotoRating(photo: PicDescPhoto): Double{
        return photo.ratingSum / (photo.usersThatVoted.size -1.0)
    }

    fun increaseChallengeCount(room: PicDescRoom, callback: () -> Unit = {}) {
        val db = Firebase.database
        val roomsRef = db.getReference("picDescRooms/${room.id}")

        val updatedNumberOfChallengesDone = room.currentNumOfChallengesDone+1
        val updatedRoom = room.copy(currentNumOfChallengesDone = updatedNumberOfChallengesDone)
        roomsRef.setValue(updatedRoom).addOnSuccessListener {
            callback()
        }

    }

    fun increaseChallengeCount(room: RePicRoom, callback: () -> Unit = {}) {
        val db = Firebase.database
        val roomsRef = db.getReference("repicRooms/${room.id}")

        val updatedNumberOfChallengesDone = room.currentNumOfChallengesDone+1
        val updatedRoom = room.copy(currentNumOfChallengesDone = updatedNumberOfChallengesDone)
        roomsRef.setValue(updatedRoom).addOnSuccessListener {
            callback()
        }

    }

    fun userSawWinnerScreen(userId: String, room: PicDescRoom, callback: () -> Unit = {}) {
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
        roomRef.setValue(updatedRoom).addOnSuccessListener {
            callback()
        }

    }

    fun userSawWinnerScreen(userId: String, room: RePicRoom, callback: () -> Unit = {}) {
        val db = Firebase.database
        val roomRef = db.getReference("repicRomms/${room.id}")

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
        roomRef.setValue(updatedRoom).addOnSuccessListener {
            callback()
        }

    }

    fun setNewLeader(userId: String, room: PicDescRoom,callback: () -> Unit={}) {
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        val updatedRoom = room.copy(currentLeader = userId)
        roomRef.setValue(updatedRoom).addOnSuccessListener {
            callback()
        }
    }

    //TODO
    fun findMostSimilarPhoto(photosSubmitted: List<RePicPhoto>, imageUrl: String, context: Context): RePicPhoto {
        var photoComparator = PhotoComparator()
        var submittedPhotoUrls = getSubmittedPhotoUrls(photosSubmitted)

        var bestPhotoIndex = 0
        var maxPhotoClassification = 0.0F

        viewModelScope.launch {

            // Get the bitmap of the model photo to repic
            val modelPhotoBitmap = getBitmap(imageUrl, context)
            photoComparator.setModelPhoto(modelPhotoBitmap)

            // Get the classification of the first submitted photo
            val firstSubmittedPhotoBitmap = getBitmap(submittedPhotoUrls[0], context)
            maxPhotoClassification = photoComparator.comparePhoto(firstSubmittedPhotoBitmap)

            // Loop through the rest of the submitted photos to find the maximum classification
            for (index in 1..(submittedPhotoUrls.size - 1)) {
                val currentPhotoBitmap = getBitmap(submittedPhotoUrls[index], context)
                val currentPhotoClassification = photoComparator.comparePhoto(currentPhotoBitmap)

                if (currentPhotoClassification > maxPhotoClassification) {
                    maxPhotoClassification = currentPhotoClassification
                    bestPhotoIndex = index
                }
            }
        }

        return photosSubmitted[bestPhotoIndex]
    }

    private fun getSubmittedPhotoUrls(photosSubmitted: List<RePicPhoto>): MutableList<String> {
        var result = mutableListOf<String>()

        for (submittedPhoto in photosSubmitted)
            result.add(submittedPhoto.photoUrl)

        return result
    }

    private suspend fun getBitmap(photoUrl: String, context: Context): Bitmap {
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(photoUrl)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable

        return (result as BitmapDrawable).bitmap
    }
}
