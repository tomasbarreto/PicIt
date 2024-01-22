package com.example.picit.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.R
import com.example.picit.entities.User
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.AppBottomMenu
import com.example.picit.utils.ScreenHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    bottomNavigationsList: List<() -> Unit> = listOf({}, {}, {}),
    name: String = "Username",
    maxPoints: String = "0",
    numberOfWins: String = "0",
    maxChallengeWinStreak: String = "0",
    nPhotosTaken: String ="0"
){

    Scaffold(
        bottomBar = { AppBottomMenu(selectedItem = 2,  onClickForItems =bottomNavigationsList) }
    ) {
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ScreenHeader(text = "$name's\n\nProfile")
            Spacer(modifier = Modifier.height(52.dp))

            Icon(Icons.Outlined.AccountCircle, contentDescription = null, modifier = Modifier.size(200.dp))
            Spacer(modifier = Modifier.height(52.dp))

            //Achievements
            // TODO in phone its unformatted
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Statistics", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier=Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Achievement("Max points", icon=Icons.Outlined.Add, value = maxPoints)
                Achievement("Number of wins", icon= Icons.Outlined.Star, value=numberOfWins)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier=Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Achievement(s="Max challenge win streak", imageId = R.drawable.fire_icon, value=maxChallengeWinStreak)
                Achievement(s="Photos taken", imageId = R.drawable.camera_icon, value=nPhotosTaken)
            }


            Spacer(modifier = Modifier.height(32.dp))

        }
    }

}

@Composable
fun Achievement(s: String, icon: ImageVector? = null, imageId: Int? = null, value:String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .border(BorderStroke(2.dp, SolidColor(Color.Gray)),RoundedCornerShape(10.dp))
            .size(height = 112.dp, width = 140.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(icon != null){
                Icon(icon, contentDescription = null, modifier = Modifier.size(28.dp))

            }
            else if (imageId != null){
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = "icon",
                    modifier = Modifier
                        .size(28.dp)
                )
            }

            Text(text = value, fontSize = 24.sp)

        }
        Text(text = s, textAlign = TextAlign.Center)
    }

}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    PicItTheme {
        UserProfileScreen()
    }
}