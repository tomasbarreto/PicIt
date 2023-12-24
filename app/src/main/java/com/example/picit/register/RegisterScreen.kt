package com.example.picit.register

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.picit.utils.ScreenHeader
import com.example.picit.ui.theme.PicItTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.core.Context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onClickBackButton: ()->Unit={}, modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var baseContext = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(text = "Register", withBackButton = true, onClickBackButton = onClickBackButton)

        Spacer(modifier = Modifier.height(200.dp))

        TextField(
            value = email,
            onValueChange = {
                if (it.length <= 40) {
                    email = it
                }
            },
            label = { Text("Email") },
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = username,
            onValueChange = {
                if (it.length <= 20) {
                    username = it
                }
            },
            label = { Text("Username") },
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = password,
            onValueChange = {
                if (it.length <= 20) {
                    password = it
                }
            },
            label = { Text("Password") },
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(onClick = {
            registerAccount(email, password, baseContext)
        }) {
            Text(text = "Register", fontSize = 22.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    PicItTheme {
        RegisterScreen()
    }
}
