package com.example.picit.camera

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicPhoto
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.Time
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage

private val TAG = "RePicCameraViewModel"

class RePicCameraViewModel: ViewModel() {


    fun submitImage(room: RePicRoom, user: User, uri: Uri){
        val storage = Firebase.storage

        val roomImageStorageRef = storage.getReference("rePic/${room.id}/" +
                "${user.username}P${room.currentNumOfChallengesDone}")

        val uploadImage = roomImageStorageRef.putFile(uri)

        uploadImage.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully
            roomImageStorageRef.downloadUrl.addOnSuccessListener { uri ->
                // Get the download URL
                val imageUrl = uri.toString()

                // Now that you have the image URL, update the Realtime Database
                updateDatabase(room, user, imageUrl)
            }
        }.addOnFailureListener { exception ->
            // Handle unsuccessful uploads
            Log.e(TAG,exception.toString())
        }

    }


    private fun updateDatabase(room: RePicRoom, user: User, imageUrl: String) {
        val db = Firebase.database
        val roomRef = db.getReference("repicRooms/${room.id}")

        // Create RePicPhoto with the image URL
        val photo = RePicPhoto(
            photoUrl = imageUrl,
            userId = user.id,
        )

        // Update the list of submitted photos in the room
        var updatedSubmittedPhotos = room.photosSubmitted.toMutableList()
        // remove previous photo submitted by the user
        updatedSubmittedPhotos = updatedSubmittedPhotos.filter { it.userId != user.id }.toMutableList()
        updatedSubmittedPhotos.add(photo)

        // Update the room object in the Realtime Database
        val updatedRoom = room.copy(photosSubmitted = updatedSubmittedPhotos)
        roomRef.setValue(updatedRoom)
    }
}