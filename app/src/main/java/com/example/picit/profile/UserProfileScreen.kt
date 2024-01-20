package com.example.picit.profile

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
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
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ScreenHeader(text = "$name's\n\nProfile")
            Spacer(modifier = Modifier.height(52.dp))

            Icon(Icons.Outlined.AccountCircle, contentDescription = null, modifier = Modifier.size(200.dp))
            Spacer(modifier = Modifier.height(52.dp))

            //Achievements
            // TODO isto no telemovel fica desformatado
            Box(
                modifier = Modifier
                    .background(Color(215, 215, 215))
                    .fillMaxWidth(0.8f)
                    .weight(1f)
                    .padding(12.dp),
            ){
                Box(
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    Text(text = "Statistics", fontSize = 32.sp)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 80.dp)
                ){
                    Achievement("Max points", icon=Icons.Outlined.Add, value = maxPoints)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 80.dp)
                ){
                    Achievement("Number of wins", icon= Icons.Outlined.Star, value=numberOfWins)
                }
                Box(
                    modifier = Modifier.align(Alignment.BottomStart)//diff padd
                ){
                    Achievement(s="Max challenge win streak", imageId = R.drawable.fire_icon, value=maxChallengeWinStreak)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 20.dp)
                ){
                    Achievement(s="Photos taken", imageId = R.drawable.camera_icon, value=nPhotosTaken)
                }

            }
            Spacer(modifier = Modifier.height(32.dp))

        }
    }

}

@Composable
fun Achievement(s: String, icon: ImageVector? = null, imageId: Int? = null, value:String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
                    contentDescription = "woman kissing the sunset",
                    modifier = Modifier
                        .size(28.dp)
                )
            }

            Text(text = value, fontSize = 24.sp)

        }
        Text(text = s, textAlign = TextAlign.Center, modifier= Modifier.width(140.dp))
        Spacer(modifier = Modifier.height(12.dp))

    }

}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    PicItTheme {
        UserProfileScreen()
    }
}