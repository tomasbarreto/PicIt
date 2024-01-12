package com.example.picit.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.Time
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database

class PicDescCameraViewModel: ViewModel() {

    fun submitImage(room: PicDescRoom, user: User, uri: Uri){
        val db = Firebase.database
        var roomRef = db.getReference("picDescRooms/${room.id}")
        // TODO: save on FirebaseStorage
        // get url

        val url = ""
        val photo = PicDescPhoto(
            photoUrl = url, userId = user.id, username = user.username,
            location = "TODO", submissionTime =  Time(), usersThatVoted =  emptyList(),
            leaderVote = false, averageRating =  0.0)

        val updatedSubmittedPhotos= room.photosSubmitted.toMutableList()
        updatedSubmittedPhotos.add(photo)

        val updatedRoom = room.copy(photosSubmitted = updatedSubmittedPhotos)
        roomRef.setValue(updatedRoom)


    }
}