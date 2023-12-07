package com.example.picit.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.picit.R

@Composable
fun LeaderboardButton(){
    Button(
        onClick = { /*TODO*/ }
    ) {
        Image(
            painter = painterResource(id = R.drawable.trofeu),
            contentDescription = "leaderboard",
            modifier = Modifier.width(32.dp)
        )
    }
}