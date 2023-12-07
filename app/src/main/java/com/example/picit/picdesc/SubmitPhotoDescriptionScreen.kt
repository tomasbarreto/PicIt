package com.example.picit.picdesc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.utils.ScreenHeader
import com.example.picit.utils.TimeLeftDisplay
import com.example.picit.leaderboard.LeaderboardButton
import com.example.picit.ui.theme.PicItTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitPhotoDescriptionScreen(
    onClickBackButton: ()->Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var roomName = "Room Name" // get in room details stored in the bd
        ScreenHeader(
            withBackButton = true,
            text = roomName,
            onClickBackButton = onClickBackButton
        )
        Spacer(modifier = Modifier.height(80.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Text(text = "You are today's leader!", fontSize = 32.sp, modifier = Modifier.align(
                Alignment.TopCenter))
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                TimeLeftDisplay(timeFor = "Submit Description\n",2,34,12)
                Spacer(modifier = Modifier.height(8.dp))

                var text by remember { mutableStateOf("") } // TODO: check if its this
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Photo Description") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(text = "Suggestions", textAlign = TextAlign.Center, fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))

                    // get this dynamically (Text generator API)
                    var suggestions = arrayOf("suggestion1","suggestion2","suggestion3")
                    for (s in suggestions){
                        Text(text = s, fontSize = 20.sp, textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 32.dp, bottom = 32.dp)){
                LeaderboardButton()
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubmitPhotoDescritpionScreenPreview() {
    PicItTheme {
        SubmitPhotoDescriptionScreen()
    }
}