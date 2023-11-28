package com.example.picit.picdesccreateroom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.R
import com.example.picit.ui.theme.PicItTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomTimeSettings(modifier: Modifier = Modifier) {
    var hoursDescRelease by remember { mutableStateOf("") }
    var minutesDescRelease by remember { mutableStateOf("") }

    var maxHours = 23
    var maxMinutes = 59

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(start = 15.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.seta_esquerda),
                    contentDescription = "back arrow",
                    modifier = Modifier.width(20.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Create room", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "Description submission time", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InsertTime()
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = " to ", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))
            InsertTime()
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Photo submission time", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InsertTime()
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = " to ", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))
            InsertTime()
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Winner announcement time", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(10.dp))

        InsertTime()

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {  }) {
            Text(text = "Create room", fontSize = 22.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTime(modifier: Modifier = Modifier) {
    var hours by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }

    var maxHours = 23
    var maxMinutes = 59

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            value = hours,
            onValueChange = { if (it == "") { hours = "" } else { if (it.toInt() <= maxHours) { if (it.length <= 2) { hours = it } } else {
                hours =
                    maxHours.toString()
            } } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            modifier = Modifier.width(70.dp)
        )

        Text(text = " : ", fontSize = 32.sp, modifier = Modifier.padding(vertical = 5.dp))

        TextField(
            value = minutes,
            onValueChange = { if (it == "") { minutes = "" } else { if (it.toInt() <= maxMinutes) { if (it.length <= 2) { minutes = it }} else {
                minutes =
                    maxMinutes.toString()
            } } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            modifier = Modifier.width(70.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PicItTheme {
        RoomTimeSettings()
    }
}
