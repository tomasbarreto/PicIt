package com.example.picit.navigation

sealed class Screens(val route:String){
    object Home: Screens("home")
    object Friends: Screens("friends")
    object Profile: Screens("profile")
}
