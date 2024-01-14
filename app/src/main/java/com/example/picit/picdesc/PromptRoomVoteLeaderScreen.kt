package com.example.picit.picdesc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.picit.R
import com.example.picit.entities.PicDescPhoto
import com.example.picit.entities.Time
import com.example.picit.leaderboard.LeaderboardButton
import com.example.picit.timer.Timer
import com.example.picit.timer.TimerViewModel
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.InvalidButton
import com.example.picit.utils.ScreenHeader
import com.example.picit.utils.ValidButton

@Composable
fun PromptRoomVoteLeader(
    onClickBackButton: ()->Unit = {},
    onClickLeaderboardButton: ()->Unit = {},
    roomName: String = "Room name",
    photoDescription:String = "photo description",
    photo: PicDescPhoto = PicDescPhoto(),
    clickValidButton: ()->Unit = {},
    clickInvalidButton: ()->Unit={},
    endingTime: Time,
    viewModel: TimerViewModel
){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            withBackButton = true,
            text = roomName,
            onClickBackButton = onClickBackButton
        )

        Spacer(modifier = Modifier.height(15.dp))
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text="Time To Vote!", fontSize = 30.sp)
        }

        PromptDisplay(photoDescription)
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if(photo.photoUrl.isNotEmpty()) {
                    Row (modifier = Modifier.height(25.dp),
                        verticalAlignment = Alignment.Bottom){
                        Icon(Icons.Filled.Person, contentDescription = null, modifier = Modifier
                            .height(25.dp)
                            .fillMaxWidth(0.1F))
                        Text(text = photo.username, modifier = Modifier
                            .height(22.dp)
                            .fillMaxWidth(0.50F))
                        Image(
                            painter = painterResource(id = R.drawable.clock),
                            contentDescription = "clock",
                            modifier = Modifier
                                .width(15.dp)
                                .fillMaxWidth(0.05F)
                        )
                        val submissionHours=String.format("%02d",photo.submissionTime.hours)
                        val submissionMins=String.format("%02d",photo.submissionTime.minutes)
                        Text(text="$submissionHours:$submissionMins", fontSize = 12.sp)
                    }
                    AsyncImage(
                        model = photo.photoUrl,
                        contentDescription = photoDescription,
                        modifier = Modifier
                            .fillMaxHeight(0.45f)
                            .fillMaxWidth(0.8f)
                    )
                    Row (modifier = Modifier.height(25.dp)) {
                        Icon(Icons.Filled.LocationOn, contentDescription = null, modifier = Modifier
                            .fillMaxWidth(0.1F))
                        Text(text = photo.location, modifier = Modifier
                            .height(22.dp)
                            .fillMaxWidth(0.55F))
                    }

                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        InvalidButton(clickInvalidButton)
                        ValidButton(clickValidButton)
                    }
                }
                else{
                    Spacer(modifier = Modifier.height(60.dp))
                    Text("There's no images for you to vote!", fontSize = 28.sp,
                        textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Wait for other users to submit photos",fontSize = 28.sp
                    , textAlign = TextAlign.Center)
                }
            }
        }



        Spacer(modifier = Modifier.weight(1f))

        Timer(timeFor = "Choose a winner!\n", viewModel = viewModel, endingTime = endingTime)

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.End) {
            LeaderboardButton(onClickLeaderboardButton)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PromptRoomVotePreview() {
    PicItTheme {
        //PromptRoomVoteLeader()
    }
}