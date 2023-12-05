package com.example.picit.picdesccreateroom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun ChooseGame(
    onClickBackButton: ()->Unit = {}
) {
    var rePicTextColor by remember { mutableStateOf(Color.hsl(hue = 196.0f, saturation = 1.0f, lightness = 0.27f)) }
    var rePicBGColor by remember { mutableStateOf(Color.LightGray) }

    var picDescTextColor by remember { mutableStateOf(Color.White) }
    var picDescBGColor by remember { mutableStateOf(Color.hsl(hue = 196.0f, saturation = 1.0f, lightness = 0.27f)) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            true,
            "Create room",
            onClickBackButton = onClickBackButton
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Select game mode", fontSize = 23.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .background(picDescBGColor)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        rePicTextColor =
                            Color.hsl(hue = 196.0f, saturation = 1.0f, lightness = 0.27f)
                        rePicBGColor = Color.LightGray

                        picDescTextColor = Color.White
                        picDescBGColor =
                            Color.hsl(hue = 196.0f, saturation = 1.0f, lightness = 0.27f)
                    }
                )
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "PicDesc", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = picDescTextColor)
                        Text(text = "Follow the description", fontSize = 26.sp, color = picDescTextColor)
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .background(rePicBGColor)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        rePicTextColor = Color.White
                        rePicBGColor = Color.hsl(hue = 196.0f, saturation = 1.0f, lightness = 0.27f)

                        picDescTextColor =
                            Color.hsl(hue = 196.0f, saturation = 1.0f, lightness = 0.27f)
                        picDescBGColor = Color.LightGray
                    }
                )
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "RePic",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = rePicTextColor
                        )
                        Text(
                            text = "Try to replicate the picture",
                            fontSize = 26.sp,
                            color = rePicTextColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {  }) {
                Text(text = "Next", fontSize = 22.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseGamePreview() {
    PicItTheme {
        ChooseGame()
    }
}