package com.example.picit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Integer.parseInt

@Composable
fun ScreenHeader(withButton: Boolean=false,text:String, headerFontSize: TextUnit = 32.sp){
    Spacer(modifier = Modifier.height(60.dp))
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        if(withButton){
            Box(
                modifier = Modifier.align(Alignment.CenterStart)
            ){
                BackButton(onButtonClick={/*TODO: go to UserRoomsScreen*/})
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center).width(220.dp)
        ) {
            Text(
                text = text,
                fontSize = headerFontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}