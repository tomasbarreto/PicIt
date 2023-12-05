package com.example.picit

import android.os.Bundle
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
import com.example.picit.navigation.Screens
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
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(it)
        ) {
            val bottomNavigationsList = listOf(
                { navController.navigate(Screens.Friends.route){ launchSingleTop = true } },
                { navController.navigate(Screens.Home.route){ launchSingleTop = true } },
                { navController.navigate(Screens.Profile.route){ launchSingleTop = true } },
                )
            composable(route= Screens.Home.route){
                UserRoomsScreen(bottomNavigationsList)
            }
            composable(route= Screens.Friends.route){
                FriendsListScreen(bottomNavigationsList)
            }
            composable(route = Screens.Profile.route){
                UserProfileScreen(bottomNavigationsList)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PicItTheme {
        UserRoomsScreen()
    }
}
