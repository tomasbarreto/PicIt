package com.example.picit.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.picit.FriendsListScreen
import com.example.picit.PreviewRoomsToJoinScreen
import com.example.picit.RoomInviteNotificationsScreen
import com.example.picit.SettingsScreen
import com.example.picit.UserProfileScreen
import com.example.picit.UserRoomsScreen
import com.example.picit.picdesccreateroom.ChooseGame
import com.example.picit.picdesccreateroom.RoomSettings
import com.example.picit.picdesccreateroom.RoomTimeSettingsPicDesc
import com.example.picit.picdesccreateroom.RoomTimeSettingsRepic

@Composable
fun PicItNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
        modifier = modifier
    ) {
        val bottomNavigationsList = listOf(
            { navController.navigate(Screens.Friends.route){ launchSingleTop = true } },
            { navController.navigate(Screens.Home.route){ launchSingleTop = true } },
            { navController.navigate(Screens.Profile.route){ launchSingleTop = true } },
        )
        val onClickBackButton= {navController.popBackStack()}
        composable(route= Screens.Home.route){
            UserRoomsScreen(
                bottomNavigationsList= bottomNavigationsList,
                onClickJoinRoom = { navController.navigate(Screens.RoomsToJoin.route)},
                onClickCreateRoom = {navController.navigate(Screens.CreateRoomChooseGame.route)},
                onClickInvitesButton = {navController.navigate(Screens.InvitesNotifications.route)},
                onClickSettings = {navController.navigate(Screens.Settings.route)}
            )
        }
        composable(route= Screens.Friends.route){
            FriendsListScreen(bottomNavigationsList= bottomNavigationsList)
        }
        composable(route = Screens.Profile.route){
            UserProfileScreen(bottomNavigationsList = bottomNavigationsList)
        }
        composable(route= Screens.RoomsToJoin.route){
            PreviewRoomsToJoinScreen(
                onClickBackButton = {onClickBackButton()}
            )
        }
        composable(route= Screens.CreateRoomChooseGame.route){
            ChooseGame(
                onClickBackButton = { onClickBackButton() },
                onClickNextButon = {
                        gameType->
                    navController.navigate(Screens.DefineRoomSettings.route
                        .replace("{game_type}",gameType))
                }
            )
        }
        composable(route = Screens.DefineRoomSettings.route){backStackEntry->
            val gameType = backStackEntry.arguments?.getString("game_type")
            val route = if( gameType.equals("0")) Screens.PicDescTimeSettings.route
                        else Screens.RePicTimeSettings.route
            RoomSettings(
                onClickBackButton = { onClickBackButton() },
                onClickNextButton = {navController.navigate( route ) }
            )
        }
        composable(route = Screens.PicDescTimeSettings.route){
            RoomTimeSettingsPicDesc(
                onClickBackButton = { onClickBackButton() }
            )
        }
        composable(route = Screens.RePicTimeSettings.route){
            RoomTimeSettingsRepic(
                onClickBackButton = { onClickBackButton() }
            )
        }
        composable(route = Screens.InvitesNotifications.route){
            RoomInviteNotificationsScreen(
                onClickBackButton = {onClickBackButton()}
            )
        }
        composable(route = Screens.Settings.route){
            SettingsScreen(
                onClickBackButton = {onClickBackButton()}
            )
        }
    }
}