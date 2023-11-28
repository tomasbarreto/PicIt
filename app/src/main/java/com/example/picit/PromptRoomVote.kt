package com.example.picit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.ui.theme.PicItTheme

@Composable
fun PromptRoomVote(){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(text = "Room name", withButton = true)

        Spacer(modifier = Modifier.height(15.dp))
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text="Time To Vote!", fontSize = 30.sp)
        }

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(50.dp)
            .background(Color.LightGray),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            Column {
                Text(text = "Prompt:", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.padding(start=5.dp)) {
                Text(text = "Pose with the sunset", fontSize = 20.sp)
            }
        }

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(200.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            Column {
                Row (modifier = Modifier.height(25.dp),
                    verticalAlignment = Alignment.Bottom){
                    Icon(Icons.Filled.Person, contentDescription = null, modifier = Modifier
                        .height(25.dp)
                        .fillMaxWidth(0.1F))
                    Text(text = "User name", modifier = Modifier
                        .height(22.dp)
                        .fillMaxWidth(0.55F))
                    Text(text="08:00", fontSize = 12.sp)
                }
                Image(
                    painter = painterResource(id = R.drawable.imagetorepic),
                    contentDescription = "woman kissing the sunset"
                )
            }
        }

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically)
        {
            ValidButton()
            InvalidButton()
        }

        TimeLeftDisplay("Vote")
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 30.dp),
            horizontalArrangement = Arrangement.End) {
            LeaderboardButton()
        }

        AppBottomMenu(inFriendScreen = false, inHomeScreen = false, inProfileScreen = false)
    }
}


@Preview(showBackground = true)
@Composable
fun RepicRoomVotePreview() {
    PicItTheme {
        PromptRoomVote()
    }
}