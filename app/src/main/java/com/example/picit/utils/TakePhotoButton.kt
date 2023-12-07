package com.example.picit.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.picit.R

@Composable
fun TakePhotoButton(onButtonClick: () -> Unit) {
    Button(
        onClick = onButtonClick,
        shape = CircleShape,
        modifier = Modifier.size(120.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.take_photo),
            contentDescription = "take photo camera",
            modifier = Modifier
                .size(80.dp)
        )
    }
}