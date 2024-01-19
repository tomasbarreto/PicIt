package com.example.picit.winner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicPhoto
import com.example.picit.entities.UserInLeaderboard
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.launch

class DailyWinnerViewModel: ViewModel() {

    private var bestRePicClassification: Float = 0.0f
    var shownRePicWinnerPhoto: RePicPhoto by mutableStateOf(RePicPhoto())
    var shownPicDescWinnerPhoto: PicDescPhoto by mutableStateOf(PicDescPhoto())
    var screenTitle: String by mutableStateOf("")
    var picDescDescription: String by mutableStateOf("")
    var rePicDescription: String by mutableStateOf("")
    private lateinit var award: Award
    private lateinit var fastestWinnerPhoto: PicDescPhoto
    private lateinit var mostVotedWinnerPhoto: PicDescPhoto
    private lateinit var modelPhotoUrl: String
    private lateinit var photoUrlsToCompare: MutableList<String>

    fun setFastestWinnerPhoto(winnerPhoto: PicDescPhoto) {
        this.fastestWinnerPhoto = winnerPhoto
    }

    fun setMostVotedWinnerPhoto(winnerPhoto: PicDescPhoto) {
        this.mostVotedWinnerPhoto = winnerPhoto
    }

    fun setPicDescDesc(description: String) {
        this.picDescDescription = description
    }

    fun setRePicDesc(description: String) {
        this.rePicDescription = description
    }

    fun setRePicWinnerPhoto(winnerPhoto: RePicPhoto) {
        this.shownRePicWinnerPhoto = winnerPhoto
    }

    fun setCurrentAward(award: Award) {
        this.award = award

        shownPicDescWinnerPhoto = if (this.award == Award.FASTEST) {
            this.fastestWinnerPhoto
        } else {
            this.mostVotedWinnerPhoto
        }

        screenTitle = if (this.award == Award.FASTEST) {
            "Fastest Award"
        } else if (this.award == Award.MOST_VOTED) {
            "Most Voted Award"
        }
        else {
            "WINNER"
        }
    }

    fun getCurrentAward(): Award {
        return this.award
    }

    fun setUserWinnerScreenVisibility(currentPicDescRoom: PicDescRoom, userID: String, visibility: Boolean) {
        val database = Firebase.database

        val currentLeaderboard = currentPicDescRoom.leaderboard
        val updatedLeaderboard = mutableListOf<UserInLeaderboard>()

        for (user in currentLeaderboard) {
            if (user.userId == userID) {
                updatedLeaderboard.add(user.copy(didSeeWinnerScreen = visibility))
            }
            else {
                updatedLeaderboard.add(user)
            }
        }

        val updatedPicDescRoom = currentPicDescRoom.copy(leaderboard = updatedLeaderboard)

        val roomRef = database.getReference("picDescRooms/${currentPicDescRoom.id}")
        roomRef.setValue(updatedPicDescRoom)
    }

    fun incrementPlayersScores(currentPicDescRoom: PicDescRoom) {
        val database = Firebase.database

        val currentLeaderboard = currentPicDescRoom.leaderboard
        val updatedLeaderboard = mutableListOf<UserInLeaderboard>()

        for (user in currentLeaderboard) {
            if (user.userId == fastestWinnerPhoto.userId || user.userId == mostVotedWinnerPhoto.userId) {
                updatedLeaderboard.add(user.copy(points = user.points + 1))
            }
            else {
                updatedLeaderboard.add(user)
            }
        }

        val updatedPicDescRoom = currentPicDescRoom.copy(leaderboard = updatedLeaderboard)

        val roomRef = database.getReference("picDescRooms/${currentPicDescRoom.id}")
        roomRef.setValue(updatedPicDescRoom)
    }

    fun incrementDailyChallenges(currentPicDescRoom: PicDescRoom) {
        val database = Firebase.database

        val updatedPicDescRoom = currentPicDescRoom.copy(currentNumOfChallengesDone = currentPicDescRoom.currentNumOfChallengesDone + 1)

        val roomRef = database.getReference("picDescRooms/${currentPicDescRoom.id}")
        roomRef.setValue(updatedPicDescRoom)
    }

    private suspend fun getBitmap(photoUrl: String, context: Context): Bitmap {
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(photoUrl)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable

        return (result as BitmapDrawable).bitmap
    }

    fun setRePicImageUrls(modelPhotoUrl: String, photosSubmitted: List<RePicPhoto>) {
        this.modelPhotoUrl = modelPhotoUrl
        this.photoUrlsToCompare = mutableListOf()

        for (photo in photosSubmitted)
            this.photoUrlsToCompare.add(photo.photoUrl)
    }

    fun compareImages(rePicSubmittedPhotos: List<RePicPhoto>, context: Context) {

        var photoComparator = PhotoComparator()

        var modelPhotoUrl = this.modelPhotoUrl
        var submittedPhotosUrl = this.photoUrlsToCompare

        var bestPhotoPhotoUrl = submittedPhotosUrl[0]
        var maxClassification = 0.0F

        viewModelScope.launch {
            var modelPhotoBitmap = getBitmap(modelPhotoUrl, context)
            photoComparator.setModelPhoto(modelPhotoBitmap)

            maxClassification = photoComparator.comparePhoto(getBitmap(submittedPhotosUrl[0], context))

            for (i in 1..submittedPhotosUrl.size - 1) {
                var currentPhotoClassification = photoComparator.comparePhoto(getBitmap(submittedPhotosUrl[i], context))

                if (currentPhotoClassification > maxClassification) {
                    maxClassification = currentPhotoClassification
                    bestPhotoPhotoUrl = submittedPhotosUrl[i]
                }
            }
        }

        for (photo in rePicSubmittedPhotos) {
            if (photo.photoUrl == bestPhotoPhotoUrl) {
                this.shownRePicWinnerPhoto = photo
                this.bestRePicClassification = maxClassification
            }
        }
    }
}
