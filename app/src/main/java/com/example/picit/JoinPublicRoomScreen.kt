package com.example.picit

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JoinPublicRoomScreen(modifier: Modifier = Modifier) {
    var roomName = "Room Name"
    var roomMaxSize = 10
    var usersInRoom = 9
    var gameType = "RePic"
    var maxDailyChallenges = 30
    var challengesDone = 13
    var pictureRelease = "17:00"
    var photoSubmission = "17:00 - 20:00"
    var limitToPicWinner = "14:00"

    var parametersTextSize = 22.sp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(start = 15.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.seta_esquerda),
                    contentDescription = "back arrow",
                    modifier = Modifier.width(20.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Join room", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        //ROOM NAME
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = roomName, fontSize = 30.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))

        // GAME TYPE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Game Type", fontSize = parametersTextSize, fontWeight = FontWeight.Bold)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = gameType, fontSize = parametersTextSize)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // CAPACITY
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Capacity", fontSize = parametersTextSize, fontWeight = FontWeight.Bold)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "$usersInRoom/$roomMaxSize", fontSize = parametersTextSize)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // CHALLENGES COMPLETED
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Challenges", fontSize = parametersTextSize, fontWeight = FontWeight.Bold)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "$challengesDone/$maxDailyChallenges", fontSize = parametersTextSize)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // SCHEDULES
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Picture Release", fontSize = parametersTextSize, fontWeight = FontWeight.Bold)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = pictureRelease, fontSize = parametersTextSize)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Photo Submission", fontSize = parametersTextSize, fontWeight = FontWeight.Bold)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = photoSubmission, fontSize = parametersTextSize)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Winner announcement", fontSize = parametersTextSize, fontWeight = FontWeight.Bold)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = limitToPicWinner, fontSize = parametersTextSize)
        }

        // JOIN BUTTON

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LeaderboardButton()

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Join room", fontSize = 22.sp)
            }
        }
    }
}