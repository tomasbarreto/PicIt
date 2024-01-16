package com.example.picit.repic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picit.network.RequestModel
import com.example.picit.network.StableDiffusionApi
import com.example.picit.network.TextPrompt
import kotlinx.coroutines.launch

private val TAG = "RepicRoomPictureReleasedViewModel"

class RepicRoomTakePictureViewModel: ViewModel() {

    fun getGeneratedImage(){

        viewModelScope.launch {
            val requestBody = RequestModel(
                listOf(
                    TextPrompt(text = "A painting of a cat", weight = 1.0),
                    TextPrompt(text= "blurry, bad", weight = -1.0)
                )
            )
            val generatedImages = StableDiffusionApi.retrofitService.getGeneratedImage(requestBody)
            Log.d(TAG, generatedImages.toString())

        }
    }
}