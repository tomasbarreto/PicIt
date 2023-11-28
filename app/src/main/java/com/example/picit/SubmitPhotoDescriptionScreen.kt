package com.example.picit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.picit.ui.theme.PicItTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitPhotoDescriptionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var roomName = "Room Name" // get in room details stored in the bd
        ScreenHeader(text = roomName, withButton = true)
        Spacer(modifier = Modifier.height(60.dp))

       Box(
           modifier = Modifier
               .fillMaxWidth()
       ){
           Column (
               modifier = Modifier
                   .fillMaxWidth()
                   .fillMaxHeight(0.95f),
               horizontalAlignment = Alignment.CenterHorizontally,
           ){
               Text(text = "You are the leader", fontSize = 32.sp)
               Spacer(modifier = Modifier.height(12.dp))
               Text(
                   text = "Provide a description for today's challenge",
                   fontSize = 20.sp,
                   textAlign = TextAlign.Center
               )
               Spacer(modifier = Modifier.height(8.dp))

               var text by remember { mutableStateOf("") } // TODO: check if its this
               TextField(
                   value = text,
                   onValueChange = { text = it },
                   label = { Text("Photo Description") },
                   modifier = Modifier.fillMaxWidth(0.8f)
               )
           }
           Box(modifier = Modifier.align(Alignment.BottomEnd).padding(end = 32.dp)){
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