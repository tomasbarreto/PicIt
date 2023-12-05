package com.example.picit.createroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.ScreenHeader
import com.example.picit.ui.theme.PicItTheme

@Composable
fun InviteFriends(
    modifier: Modifier = Modifier,
    onClickBackButton: ()->Unit = {}
    ) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            withBackButton = true,
            text = "Create room",
            onClickBackButton = onClickBackButton
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text("Invite your friends", fontSize = 23.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(40.dp))

        var friends = 4;
        for (i in 1..friends) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 25.dp)
                    .clip(shape = RoundedCornerShape(50.dp, 50.dp, 50.dp, 50.dp))
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 25.dp, end = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Friend name", fontSize = 23.sp)

                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Invite", fontSize = 18.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {  }) {
            Text(text = "Create room", fontSize = 22.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InviteFriendsPreview() {
    PicItTheme {
        InviteFriends()
    }
}