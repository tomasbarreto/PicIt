package com.example.picit.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.entities.GameType
import com.example.picit.entities.JoinRoomRequest
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.User
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@Composable
fun RoomInviteNotificationsScreen(
    onClickBackButton: ()->Unit = {},
    viewModel: RoomInviteNotificationsViewModel,
    currentUser: User = User()
){
    var requestsWithRepicRooms = viewModel.requestsWithRepicRooms
    var requestsWithPicDescRooms = viewModel.requestsWithPicDescRooms

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            withBackButton = true,
            text = "Requests",
            onClickBackButton = onClickBackButton
        )
        requestsWithRepicRooms.forEach { reqRoom ->
            RequestPanel(roomRequest = true, joinRoomRequest = reqRoom.first, repic = reqRoom.second,
                rejectFunction = {
                    viewModel.removeRoomRequests(requestRepic = reqRoom, user = currentUser, gameType = GameType.REPIC)
                })
        }
        requestsWithPicDescRooms.forEach { reqRoom ->
            RequestPanel(roomRequest = true, joinRoomRequest = reqRoom.first, picdesc = reqRoom.second,
                rejectFunction = {
                    viewModel.removeRoomRequests(requestPicDesc = reqRoom, user = currentUser, gameType = GameType.PICDESC)
                })
        }
//        RequestPanel(true)
//        RequestPanel(false)
//        RequestPanel(true)
    }
}

@Composable
fun RequestPanel(roomRequest:Boolean, joinRoomRequest: JoinRoomRequest,
                 repic: RePicRoom = RePicRoom(), picdesc: PicDescRoom = PicDescRoom(),
                 rejectFunction: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp, top = 10.dp, start = 30.dp, end = 30.dp)
            .clip(shape = RoundedCornerShape(50.dp, 50.dp, 50.dp, 50.dp))
            .background(Color.LightGray)
            .fillMaxWidth())
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 20.dp, top = 20.dp)
        )
        {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (roomRequest) {
                    Text(text = "${joinRoomRequest.gameType} Room Request", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                } else {
                    Text(text = "Friend Request", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Icon(
                    Icons.Filled.Person, contentDescription = null
                )
                if (roomRequest) {
                    val roomName = if(joinRoomRequest.gameType.equals(GameType.REPIC)) repic.name else picdesc.name
                    Text(text = "${joinRoomRequest.senderName} is asking you to join his room ${roomName}.", modifier = Modifier.width(230.dp), textAlign = TextAlign.Center)
                } else {
                    Text(text = "User name is asking you to be your friend.", modifier = Modifier.width(230.dp), textAlign = TextAlign.Center)
                }
            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RejectAcceptButton("Reject", rejectFunction)
                RejectAcceptButton("Accept")
            }
        }
    }
}


@Composable
fun RejectAcceptButton(decision: String, acceptRejectFunction: () -> Unit = {}){
    Button(
        onClick = { acceptRejectFunction() }
    ) {
        Text(text = decision)
    }
}




@Preview(showBackground = true)
@Composable
fun RoomInviteNotificationsScreenPreview() {
    PicItTheme {
//        RoomInviteNotificationsScreen(viewModel = viewModel())
    }
}