package com.example.picit.winner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.leaderboard.LeaderboardButton
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@Composable
fun RoomWinnerScreen(
    roomName: String = "Room name",
    username: String = "username",
    winnerPoints: Int = 100,
    onClickBackButton: () -> Unit = {},
    onClickLeaveButton: () -> Unit = {},
    onClickLeaderboardButton: () -> Unit = {}
) {

    Scaffold(
        floatingActionButton = { LeaderboardButton({onClickLeaderboardButton()}) }
    ) {
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ScreenHeader(text = "$roomName", withBackButton = true, onClickBackButton = onClickBackButton)
            Text(text = "WINNER", fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(52.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Icon(Icons.Outlined.AccountCircle, contentDescription = null, modifier = Modifier.size(200.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = username.uppercase(), fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Total points: $winnerPoints",  fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = {onClickLeaveButton()}) {
                Text(text = "Leave Room", fontSize = 24.sp, modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomWinnerPreview() {
    PicItTheme {
        RoomWinnerScreen()
    }
}