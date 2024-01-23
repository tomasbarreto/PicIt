package com.example.picit.repic

import android.graphics.Bitmap
import kotlin.math.min

class PhotoComparator {

    fun comparePhoto(modelPhoto: Bitmap, photoToCompare: Bitmap): Float {
        val modelMutableBitmap = modelPhoto.copy(Bitmap.Config.RGBA_F16, true)
        val photoToCompareMutableBitmap = photoToCompare.copy(Bitmap.Config.RGBA_F16, true)

        val width = min(modelMutableBitmap.width, photoToCompareMutableBitmap.width)
        val height = min(modelMutableBitmap.height, photoToCompareMutableBitmap.height)

        var counter = 0F

        for (i in 3*width/7..4*width/7) {
            for (j in 3*width/7..4*height/7) {
                if (inPixelInPixelRange(modelMutableBitmap.getPixel(i, j), photoToCompareMutableBitmap.getPixel(i, j))) {
                    counter++
                }
            }
        }

        return counter / (height * width)
    }

    private fun inPixelInPixelRange(pixel1: Int, pixel2: Int): Boolean {
        return pixel1 <= pixel2 + 20 && pixel1 >= pixel2 - 20
    }
}