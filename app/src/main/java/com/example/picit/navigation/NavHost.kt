package com.example.picit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.picit.camera.CameraScreen
import com.example.picit.entities.User
import com.example.picit.friendslist.FriendsListScreen
import com.example.picit.joinroom.PreviewRoomsToJoinScreen
import com.example.picit.joinroom.UserRoomsScreen
import com.example.picit.login.LoginScreen
import com.example.picit.login.LoginViewModel
import com.example.picit.notifications.RoomInviteNotificationsScreen
import com.example.picit.picdesc.PromptRoomTakePicture
import com.example.picit.picdesccreateroom.ChooseGameScreen
import com.example.picit.picdesccreateroom.RoomSettingsScreen
import com.example.picit.picdesccreateroom.RoomTimeSettingsPicDescScreen
import com.example.picit.picdesccreateroom.RoomTimeSettingsRepicScreen
import com.example.picit.profile.UserProfileScreen
import com.example.picit.register.RegisterScreen
import com.example.picit.repic.RepicRoomTakePicture
import com.example.picit.settings.SettingsScreen

private val TAG = "NavHost"

@Composable
fun PicItNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    var loginViewModel : LoginViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.Login.route,
        modifier = modifier
    ) {
        val bottomNavigationsList = listOf(
            { navController.navigate(Screens.Friends.route){ launchSingleTop = true } },
            { navController.navigate(Screens.Home.route){ launchSingleTop = true } },
            { navController.navigate(Screens.Profile.route){ launchSingleTop = true } },
        )
        val onClickBackButton = {navController.popBackStack()}
        val onClickCameraButton = {navController.navigate(Screens.Camera.route)}
        val onClickGoToRegistry = {navController.navigate(Screens.Register.route)}
        val onClickGoBackToLogin = {navController.navigate(Screens.Login.route)}
        val onClickGoToMainScreen = {navController.navigate(Screens.Home.route)}

        // done with id instead of the user, because updates with the user needed to be done with
        // listeners and that would have very complex logic
        var currentUserId by mutableStateOf("")

        composable(route= Screens.Login.route) {
            val currentUserUpdate = {
                newCurrentUserId: String ->
                currentUserId = newCurrentUserId
            }

            LoginScreen(
                onClickGoToRegistry = onClickGoToRegistry,
                onClickGoToMainScreen = onClickGoToMainScreen,
                currentUserUpdate = currentUserUpdate,
                viewModel = loginViewModel
            )
        }
        composable(route= Screens.Register.route) {
            RegisterScreen(onClickBackButton = {onClickBackButton()}, onClickGoBackToLogin = onClickGoBackToLogin)
        }
        composable(route= Screens.Home.route) {
            var currentUser = remember{
                mutableStateOf(User())
            }
            loginViewModel.findUserById(currentUserId, { user: User -> currentUser.value = user })

            UserRoomsScreen(
                bottomNavigationsList= bottomNavigationsList,
                onClickJoinRoom = { navController.navigate(Screens.RoomsToJoin.route)},
                onClickCreateRoom = {navController.navigate(Screens.CreateRoomChooseGame.route)},
                onClickInvitesButton = {navController.navigate(Screens.InvitesNotifications.route)},
                onClickSettings = {navController.navigate(Screens.Settings.route)},
                onClickRooms = {navController.navigate(Screens.RepicRoomTakePicture.route)},
                currentUserRoomsIds = currentUser.value.repicRooms
            )
        }
        composable(route= Screens.Friends.route){
            FriendsListScreen(bottomNavigationsList= bottomNavigationsList)
        }
        composable(route = Screens.Profile.route){
            var currentUser = remember{
                mutableStateOf(User())
            }
            loginViewModel.findUserById(currentUserId, { user: User -> currentUser.value = user }) // tem de se esperar por isto

            UserProfileScreen(
                bottomNavigationsList = bottomNavigationsList,
                name=currentUser.value.name,
                maxPoints = currentUser.value.maxPoints.toString(),
                numberOfWins = currentUser.value.totalWins.toString(),
                maxChallengeWinStreak = currentUser.value.maxWinStreak.toString(),
                nPhotosTaken = currentUser.value.nrPhotosTaken.toString()
            )
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
        composable(route = Screens.DefineRoomSettings.route){ backStackEntry->
            val gameType = backStackEntry.arguments?.getString("game_type")
            val route = if( gameType.equals("0")) Screens.PicDescTimeSettings.route
                        else Screens.RePicTimeSettings.route

            RoomSettingsScreen(
                onClickBackButton = { onClickBackButton() },
                onClickNextButton = {
                        name, capacity, numChallenges ->
                    navController.navigate(
                        route
                            .replace("{roomName}", name)
                            .replace("{capacity}", capacity)
                            .replace("{numChallenges}", numChallenges)
                    )
                }
            )
        }
        composable(route = Screens.PicDescTimeSettings.route) { backStackEntry->
            val roomName = backStackEntry.arguments?.getString("roomName")
            val roomCapacity = backStackEntry.arguments?.getString("capacity")
            val numChallenges = backStackEntry.arguments?.getString("numChallenges")

            var currentUser = remember{
                mutableStateOf(User())
            }
            loginViewModel.findUserById(currentUserId, { user: User -> currentUser.value = user })

            if (roomName != null && roomCapacity != null && numChallenges != null) {
                RoomTimeSettingsPicDescScreen(
                    onClickBackButton = { onClickBackButton() },
                    roomName = roomName,
                    roomCapacity = roomCapacity,
                    numChallenges = numChallenges,
                    onClickGoHomeScreen = onClickGoToMainScreen,
                    currentUserId = currentUserId,
                    currentUserRooms = currentUser.value.picDescRooms
                )
            }
        }
        composable(route = Screens.RePicTimeSettings.route){ backStackEntry->
            val roomName = backStackEntry.arguments?.getString("roomName")
            val roomCapacity = backStackEntry.arguments?.getString("capacity")
            val numChallenges = backStackEntry.arguments?.getString("numChallenges")

            var currentUser = remember{
                mutableStateOf(User())
            }
            loginViewModel.findUserById(currentUserId, { user: User -> currentUser.value = user })

            if (roomName != null && roomCapacity != null && numChallenges != null) {
                RoomTimeSettingsRepicScreen(
                    onClickBackButton = { onClickBackButton() },
                    roomName = roomName,
                    roomCapacity = roomCapacity,
                    numChallenges = numChallenges,
                    onClickGoHomeScreen = onClickGoToMainScreen,
                    currentUserId = currentUserId,
                    currentUserRooms = currentUser.value.repicRooms
                )
            }
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
        composable(route= Screens.Camera.route){
            CameraScreen(onClickBackButton = {onClickBackButton()})
        }
        composable(route = Screens.PromptRoomTakePicture.route){
            PromptRoomTakePicture(
                onClickBackButton = {onClickBackButton()},
                onClickCameraButton = onClickCameraButton
            )
        }
        composable(route = Screens.RepicRoomTakePicture.route){
            RepicRoomTakePicture(
                onClickBackButton = {onClickBackButton()},
                onClickCameraButton = onClickCameraButton
            )
        }
    }
}


