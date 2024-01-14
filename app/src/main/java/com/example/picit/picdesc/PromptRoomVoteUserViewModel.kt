package com.example.picit.picdesc

import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database

class PromptRoomVoteUserViewModel: ViewModel() {

    fun userVote(user: User,room: PicDescRoom ,photo: PicDescPhoto, raiting:Int){
        val db = Firebase.database
        val roomRef = db.getReference("picDescRoom/${room.id}}")

        val updatedUsersThatVoted = photo.usersThatVoted.toMutableList()
        updatedUsersThatVoted.add(user.id)
        val photoUpdated = photo.copy(usersThatVoted = updatedUsersThatVoted)

        // remove this photo to add the updated one
        val updatedRoomPhotos = room.photosSubmitted.filter{ it.userId != photo.userId}.toMutableList()
        updatedRoomPhotos.add(photoUpdated)
        val roomUpdated = room.copy(photosSubmitted = updatedRoomPhotos)
        roomRef.setValue(roomUpdated)
    }
}