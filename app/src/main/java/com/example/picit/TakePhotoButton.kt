package com.example.picit

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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