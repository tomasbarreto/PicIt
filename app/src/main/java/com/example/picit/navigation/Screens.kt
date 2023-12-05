package com.example.picit.navigation

sealed class Screens(val route:String){
    object Home: Screens("home")
    object Friends: Screens("friends")
    object Profile: Screens("profile")
    object RoomsToJoin: Screens("rooms_to_join")
    object CreateRoomChooseGame: Screens("create_room_choose_game")
    object InvitesNotifications: Screens("invite_notifications")
    object Settings: Screens("settings")
}