package com.example.picit.picdesc

import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database

class PromptRoomVoteUserViewModel: ViewModel() {

    fun userVote(user: User,room: PicDescRoom ,photo: PicDescPhoto, rating:Int){
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        val updatedUsersThatVoted = photo.usersThatVoted.toMutableList()
        updatedUsersThatVoted.add(user.id)
        val updatedRating = photo.ratingSum + rating
        val photoUpdated = photo.copy(usersThatVoted = updatedUsersThatVoted, ratingSum = updatedRating)

        val index = room.currentNumOfChallengesDone
        val photosSubmittedInChallenge = room.allPhotosSubmitted[index]
        val photosSubmittedInChallengeUpdated = photosSubmittedInChallenge.filter { it.userId != photo.userId }.toMutableList()
        photosSubmittedInChallengeUpdated.add(photoUpdated)

        val photosSubmittedAllChallenges = room.allPhotosSubmitted.toMutableList()
        photosSubmittedAllChallenges[index] = photosSubmittedInChallengeUpdated

        val roomUpdated = room.copy(allPhotosSubmitted = photosSubmittedAllChallenges)
        roomRef.setValue(roomUpdated)
    }
}