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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
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
fun RoomInviteNotificationsScreen(){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(text = "Requests", withButton = true)
        RequestPanel(true)
        RequestPanel(false)
        RequestPanel(true)


    }
}

@Composable
fun RequestPanel(roomRequest:Boolean) {
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(30.dp)
        .background(Color.LightGray)) {
        Row (modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            if(roomRequest) {
                Text(text = "Room Request", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            } else {
                Text(text = "Friend Request", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        Row(modifier = Modifier.padding(10.dp)) {
            Icon(
                Icons.Filled.Person, contentDescription = null)
            if(roomRequest) {
                Text(text = "User name is asking you to join his room.")
            } else {
                Text(text = "User name is asking you to be your friend.")
            }
        }
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            RejectAcceptButton("Reject")
            RejectAcceptButton("Accept")
        }




    }

}


@Composable
fun RejectAcceptButton(decision: String){
    Button(
        onClick = { /*TODO*/ }
    ) {
        Text(text = decision)
    }
}




@Preview(showBackground = true)
@Composable
fun RoomInviteNotificationsScreenPreview() {
    PicItTheme {
        RoomInviteNotificationsScreen()
    }
}