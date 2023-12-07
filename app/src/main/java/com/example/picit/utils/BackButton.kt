package com.example.picit.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.picit.R

@Composable
fun BackButton(onButtonClick: () -> Unit) {
    Button(
        onClick = onButtonClick,
        modifier = Modifier.padding(start = 15.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.seta_esquerda),
            contentDescription = "back arrow",
            modifier = Modifier.width(20.dp)
        )
    }
}