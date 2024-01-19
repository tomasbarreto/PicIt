package com.example.picit.winner

import android.graphics.Bitmap
import androidx.core.graphics.get

class PhotoComparator {

    private lateinit var modelPhoto: Bitmap

    fun setModelPhoto(modelPhoto: Bitmap) {
        this.modelPhoto = modelPhoto
    }
    fun comparePhoto(photoToCompare: Bitmap): Float {
        val width = getWidthForComparison(photoToCompare)
        val height = getHeightForComparison(photoToCompare)

        var counter = 0F

        for (i in 0..height - 1) {
            for (j in 0..width - 1) {
                if (photoToCompare[i, j] == modelPhoto[i, j]) {
                    counter++
                }
            }
        }

        return counter / (height * width)
    }

    private fun getWidthForComparison(photoToCompare: Bitmap): Int {
        val width = if (modelPhoto.width < photoToCompare.width) {
            modelPhoto.width
        }
        else {
            photoToCompare.width
        }

        return width
    }

    private fun getHeightForComparison(photoToCompare: Bitmap): Int {
        val height = if (modelPhoto.height < photoToCompare.height) {
            modelPhoto.height
        }
        else {
            photoToCompare.height
        }

        return height
    }
}