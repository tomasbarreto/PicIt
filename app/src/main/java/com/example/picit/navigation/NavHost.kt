package com.example.picit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.picit.friendslist.FriendsListScreen
import com.example.picit.joinroom.PreviewRoomsToJoinScreen
import com.example.picit.notifications.RoomInviteNotificationsScreen
import com.example.picit.settings.SettingsScreen
import com.example.picit.profile.UserProfileScreen
import com.example.picit.joinroom.UserRoomsScreen
import com.example.picit.picdesccreateroom.ChooseGameScreen
import com.example.picit.picdesccreateroom.RoomSettingsScreen
import com.example.picit.picdesccreateroom.RoomTimeSettingsPicDescScreen
import com.example.picit.picdesccreateroom.RoomTimeSettingsRepicScreen

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
            ChooseGameScreen(
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
            RoomSettingsScreen(
                onClickBackButton = { onClickBackButton() },
                onClickNextButton = {navController.navigate( route ) }
            )
        }
        composable(route = Screens.PicDescTimeSettings.route){
            RoomTimeSettingsPicDescScreen(
                onClickBackButton = { onClickBackButton() }
            )
        }
        composable(route = Screens.RePicTimeSettings.route){
            RoomTimeSettingsRepicScreen(
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