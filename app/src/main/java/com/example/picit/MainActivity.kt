package com.example.picit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.picit.navigation.PicItNavHost
import com.example.picit.navigation.Screens
import com.example.picit.picdesccreateroom.ChooseGame
import com.example.picit.ui.theme.PicItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PicItTheme {
                PicItApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicItApp() {
    // A surface container using the 'background' color from the theme
    val navController = rememberNavController()
    Scaffold {
        PicItNavHost(navController = navController, modifier=Modifier.padding(it))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PicItTheme {
        UserRoomsScreen()
    }
}
