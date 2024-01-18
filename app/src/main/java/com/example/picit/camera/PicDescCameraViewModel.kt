package com.example.picit.camera

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.Time
import com.example.picit.entities.User
import com.example.picit.location.LocationClient
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import java.util.Calendar

class PicDescCameraViewModel: ViewModel() {

    fun submitImage(room: PicDescRoom, user: User, uri: Uri, context: Context){
        val storage = Firebase.storage


        val roomImageStorageRef = storage.getReference("picDesc/${room.id}/" +
                "${user.username}P${room.currentNumOfChallengesDone}")

        val uploadImage = roomImageStorageRef.putFile(uri)

        uploadImage.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully
            roomImageStorageRef.downloadUrl.addOnSuccessListener { uri ->
                // Get the download URL
                val imageUrl = uri.toString()

                // Now that you have the image URL, update the Realtime Database
                updateDatabase(room, user, imageUrl, context)
            }
        }.addOnFailureListener { exception ->
            // Handle unsuccessful uploads
        }


    }


    private fun updateDatabase(room: PicDescRoom, user: User, imageUrl: String, context: Context) {
        val locationClient = LocationClient()
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        val currentCalendar = Calendar.getInstance()
        val currentTime = Time(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(
            Calendar.MINUTE))

        var location = ""

        if (locationClient.isLocationPermGranted(context)) {
            locationClient.startLocationClient(context)
            location = locationClient.getLocation(context)
        }

        // Create PicDescPhoto with the image URL
        val photo = PicDescPhoto(
            photoUrl = imageUrl,
            userId = user.id,
            username = user.username,
            location = location,
            submissionTime = currentTime,
            usersThatVoted = emptyList(),
            leaderVote = false,
            ratingSum = 0
        )

        // Update the list of submitted photos in the room
        val updatedSubmittedPhotos = room.photosSubmitted.filter{
            it.userId != user.id
        }.toMutableList()
        updatedSubmittedPhotos.add(photo)

        // Update the room object in the Realtime Database
        val updatedRoom = room.copy(photosSubmitted = updatedSubmittedPhotos)
        roomRef.setValue(updatedRoom)
    }
}