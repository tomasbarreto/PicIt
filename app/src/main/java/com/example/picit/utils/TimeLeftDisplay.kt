package com.example.picit.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeLeftDisplay(timeFor: String, hours:Int, mins:Int, secs:Int) {
    Column {
        Column (modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text= "$timeFor Time Remaining:", textAlign = TextAlign.Center, fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row (modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            TimeColumn(timeUnit = "Hours", timeLeft = hours)
            TimeColumn(timeUnit = "Minutes", timeLeft = mins)
            TimeColumn(timeUnit = "Seconds", timeLeft = secs)
        }
    }
}

@Composable
fun TimeColumn(timeUnit: String, timeLeft: Int) {
    Column (modifier = Modifier
        .padding(5.dp)
        .width(90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)

    {
        Text(text= String.format("%02d",timeLeft), fontSize = 30.sp)
        Text(text= timeUnit, fontSize = 20.sp)
    }
}