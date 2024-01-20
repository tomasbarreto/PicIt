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

        for (i in 0..(width - 1)) {
            for (j in 0..(height - 1)) {
                if (inPixelRoom(modelMutableBitmap.getPixel(i, j), photoToCompareMutableBitmap.getPixel(i, j))) {
                    counter++
                }
            }
        }

        return counter / (height * width)
    }

    private fun inPixelRoom(pixel1: Int, pixel2: Int): Boolean {
        return pixel1 <= pixel2 + 20 && pixel1 >= pixel2 - 20
    }
}