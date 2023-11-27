package com.example.picit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LeaderboardButton(onButtonClick: () -> Unit) {
    Button(
        onClick = onButtonClick
    ) {
        Image(
            painter = painterResource(id = R.drawable.trofeu),
            contentDescription = "trophy",
            modifier = Modifier
                .size(35.dp)
                .padding(start = 5.dp, end = 5.dp)
        )
    }
}