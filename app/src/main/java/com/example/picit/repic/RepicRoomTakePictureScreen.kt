package com.example.picit.repic


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import com.example.picit.R
import com.example.picit.entities.RePicRoom
import com.example.picit.leaderboard.LeaderboardButton
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader
import com.example.picit.utils.TakePhotoButton
import com.example.picit.utils.TimeLeftDisplay

@Composable
fun RepicRoomTakePicture(
    onClickBackButton: ()->Unit = {},
    onClickCameraButton: ()->Unit = {},
    room: RePicRoom
){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            withBackButton = true,
            text = room.name,
            onClickBackButton = onClickBackButton
        )

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

        TimeLeftDisplay("Submit Photo",1,24,32)
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            horizontalArrangement = Arrangement.Center) {
            TakePhotoButton(onButtonClick = onClickCameraButton)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 20.dp),
            horizontalArrangement = Arrangement.End) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LeaderboardButton()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RepicRoomTakePicturePreview() {
    PicItTheme {
//        RepicRoomTakePicture()
    }
}