package com.example.picit

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.ui.theme.PicItTheme

@Composable
fun RepicRoomTakePicture(){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(60.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            BackButton(onButtonClick={/*TODO: go to UserRoomsScreen*/})

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RoomHeader()
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text="Photo To Repic:", fontSize = 30.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagetorepic),
                contentDescription = "woman kissing the sunset",
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        TimeLeftDisplay("Photo")
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start=20.dp, end=20.dp, top=65.dp, bottom = 120.dp)) {
            Column (modifier = Modifier.weight(0.70F),
                horizontalAlignment = Alignment.CenterHorizontally){
                TakePhotoButton(onButtonClick = {/*TODO: go to Camera*/ })
            }
            Column(modifier = Modifier.weight(0.30F),
                horizontalAlignment = Alignment.CenterHorizontally) {
                LeaderboardButton(onButtonClick = {/*TODO: go to Leaderboard*/ })
            }
        }









        AppBottomMenu(inFriendScreen = false, inHomeScreen = false, inProfileScreen = false)
    }
}


@Preview(showBackground = true)
@Composable
fun RepicRoomTakePicturePreview() {
    PicItTheme {
        RepicRoomTakePicture()
    }
}