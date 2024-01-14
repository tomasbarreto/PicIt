package com.example.picit.winner

import androidx.lifecycle.ViewModel
import com.example.picit.entities.PicDescPhoto
import com.example.picit.picdesc.Award

class DailyWinnerViewModel: ViewModel() {

    private lateinit var fastestWinnerPhoto: PicDescPhoto
    private lateinit var mostVotedWinnerPhoto: PicDescPhoto
    private lateinit var award: Award
    private lateinit var picDescDescription: String

    fun setFastestWinnerPhoto(winnerPhoto: PicDescPhoto) {
        this.fastestWinnerPhoto = winnerPhoto
    }

    fun setMostVotedWinnerPhoto(winnerPhoto: PicDescPhoto) {
        this.mostVotedWinnerPhoto = winnerPhoto
    }

    fun setPicDescDescription(description: String) {
        this.picDescDescription = description
    }

    fun getPicDescDescription(): String {
        return "\n" + this.picDescDescription + "\n"
    }

    fun setAward(award: Award) {
        this.award = award
    }

    fun getScreenTitle(): String {
        when(this.award) {
            Award.FASTEST -> return "Fastest Award"
            Award.MOST_VOTED -> return "Most Voted Award"
            else -> {}
        }

        return ""
    }

    fun getUsername(): String {
        when(this.award) {
            Award.FASTEST -> return fastestWinnerPhoto.username
            Award.MOST_VOTED -> return mostVotedWinnerPhoto.username
            else -> {}
        }

        return ""
    }

    fun getLocation(): String {
        when(this.award) {
            Award.FASTEST -> return fastestWinnerPhoto.location
            Award.MOST_VOTED -> return mostVotedWinnerPhoto.location
            else -> {}
        }

        return ""
    }

    fun getPhotoUrl(): String {
        when(this.award) {
            Award.FASTEST -> return fastestWinnerPhoto.photoUrl
            Award.MOST_VOTED -> return mostVotedWinnerPhoto.photoUrl
            else -> {}
        }

        return ""
    }

    fun getTimeStamp(): String {
        when(this.award) {
            Award.FASTEST -> return fastestWinnerPhoto.submissionTime.hours.toString() + ":" + fastestWinnerPhoto.submissionTime.minutes.toString()
            Award.MOST_VOTED -> return mostVotedWinnerPhoto.submissionTime.hours.toString() + ":" + fastestWinnerPhoto.submissionTime.minutes.toString()
            else -> {}
        }

        return ""
    }

    fun getRating(): String {
        when(this.award) {
            Award.FASTEST -> return (fastestWinnerPhoto.ratingSum / (fastestWinnerPhoto.usersThatVoted.size - 1.0)).toString()
            Award.MOST_VOTED -> return (mostVotedWinnerPhoto.ratingSum / (mostVotedWinnerPhoto.usersThatVoted.size - 1.0)).toString()
            else -> {}
        }

        return ""
    }
}
