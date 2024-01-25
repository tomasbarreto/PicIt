package com.example.picit.picdesc

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.User
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.database
import com.google.firebase.database.getValue

private val TAG = "PromptRoomVoteLeaderViewModel"

class PromptRoomVoteLeaderViewModel: ViewModel() {

    fun leaderVote(photo: PicDescPhoto, user: User, room:PicDescRoom, vote:Boolean){
        val db = Firebase.database
        val roomRef = db.getReference("picDescRooms/${room.id}")

        roomRef.runTransaction(object : Transaction.Handler{
            override fun doTransaction(currentData: MutableData): Transaction.Result {
                val currentRoom = currentData.getValue<PicDescRoom>()!!
                var usersThatVoteUpdated = photo.usersThatVoted.toMutableList()
                usersThatVoteUpdated.add(user.id)

                var photoUpdated = photo.copy(leaderVote = vote, usersThatVoted = usersThatVoteUpdated)

                val index = currentRoom.currentNumOfChallengesDone
                val photosSubmittedInChallenge = currentRoom.allPhotosSubmitted[index]
                val photosSubmittedInChallengeUpdated = photosSubmittedInChallenge
                                                .filter { it.userId != photo.userId }.toMutableList()
                photosSubmittedInChallengeUpdated.add(photoUpdated)

                val photosSubmittedAllChallenges = currentRoom.allPhotosSubmitted.toMutableList()
                photosSubmittedAllChallenges[index] = photosSubmittedInChallengeUpdated

                val roomUpdated = currentRoom.copy(allPhotosSubmitted = photosSubmittedAllChallenges)
                currentData.value = roomUpdated
                return Transaction.success(currentData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
                if (committed) {
                    Log.d(TAG,currentData.toString())
                } else {
                    // Handle the case where the transaction failed
                }
            }
        })

    }
}