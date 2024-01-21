package com.example.picit.winner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.StrictMode
import android.util.Base64
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
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

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


    fun awardUser(photo: RePicPhoto, room: RePicRoom, points:Int =1, callback: ()->Unit = {}) {
        val db = Firebase.database
        val roomRef = db.getReference("repicRooms/${room.id}")

        var userInLeaderboard = UserInLeaderboard()
        val updatedLeaderboard = room.leaderboard.toMutableList()

        val userId = photo.userId
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

        val updatedWinners = room.winners.toMutableList()
        updatedWinners.add(photo)

        val updatedRoom = room.copy(leaderboard = updatedLeaderboard, winners = updatedWinners)
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
        if (photo.ratingSum == 0) return 0.0
        return photo.ratingSum / (photo.usersThatVoted.size -1.0)
    }

    fun increaseChallengeCount(room: PicDescRoom, callback: () -> Unit = {}) {
        val db = Firebase.database
        val roomsRef = db.getReference("picDescRooms/${room.id}")

        val updatedNumberOfChallengesDone =
            if (room.currentNumOfChallengesDone<room.maxNumOfChallenges)
                room.currentNumOfChallengesDone+1
            else
                room.maxNumOfChallenges
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
        val roomRef = db.getReference("repicRooms/${room.id}")

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

    fun findMostSimilarPhoto(photosSubmitted: List<RePicPhoto>, imageUrl: String, context: Context): RePicPhoto {
        var photoComparator = PhotoComparator()
        var submittedPhotoUrls = getSubmittedPhotoUrls(photosSubmitted)

        var bestPhotoIndex = 0
        var maxPhotoClassification = 0.0F


        // Get the bitmap of the model photo to repic
        val modelPhotoBitmap = imageUrlToBitmap(imageUrl)

        // Get the classification of the first submitted photo
        val firstSubmittedPhotoBitmap = imageUrlToBitmap(submittedPhotoUrls[0])
        maxPhotoClassification = photoComparator.comparePhoto(modelPhotoBitmap, firstSubmittedPhotoBitmap)

        // Loop through the rest of the submitted photos to find the maximum classification
        for (index in 1..(submittedPhotoUrls.size - 1)) {
            val currentPhotoBitmap = imageUrlToBitmap(submittedPhotoUrls[index])
            val currentPhotoClassification = photoComparator.comparePhoto(modelPhotoBitmap, currentPhotoBitmap)

            if (currentPhotoClassification > maxPhotoClassification) {
                maxPhotoClassification = currentPhotoClassification
                bestPhotoIndex = index
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


    private fun convertImageToBase64(imageUrl: String): String {
        val policy = StrictMode.ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val url = URL(imageUrl)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val inputStream: InputStream = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun imageUrlToBitmap(imageUrl: String): Bitmap {
        val base64String = convertImageToBase64(imageUrl)
        val decodedBytes: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
        val inputStream = ByteArrayInputStream(decodedBytes)
        return BitmapFactory.decodeStream(inputStream)
    }
}
