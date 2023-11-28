package com.example.picit

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
        Row (modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text= "$timeFor Time Remaining:", fontSize = 25.sp, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row (modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
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
        Text(text= if (timeLeft<10) "0$timeLeft" else timeLeft.toString(), fontSize = 30.sp)
        Text(text= timeUnit, fontSize = 20.sp)
    }
}