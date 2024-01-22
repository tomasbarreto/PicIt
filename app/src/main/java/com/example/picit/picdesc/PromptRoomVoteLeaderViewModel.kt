package com.example.picit.picdesc

import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.database

class PromptRoomVoteLeaderViewModel: ViewModel() {

    fun leaderVote(photo: PicDescPhoto, user: User, room:PicDescRoom, vote:Boolean){
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        var usersThatVoteUpdated = photo.usersThatVoted.toMutableList()
        usersThatVoteUpdated.add(user.id)

        var photoUpdated = photo.copy(leaderVote = vote, usersThatVoted = usersThatVoteUpdated)
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