package com.example.picit.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.picit.camera.CameraScreen
import com.example.picit.entities.GameType
import com.example.picit.entities.PicDescRoom
import com.example.picit.entities.RePicRoom
import com.example.picit.entities.Time
import com.example.picit.entities.User
import com.example.picit.friendslist.FriendsListScreen
import com.example.picit.joinroom.JoinPicDescRoomScreen
import com.example.picit.joinroom.JoinPicDescRoomViewModel
import com.example.picit.joinroom.JoinRepicRoomScreen
import com.example.picit.joinroom.JoinRepicRoomViewModel
import com.example.picit.joinroom.PreviewRoomsToJoinScreen
import com.example.picit.joinroom.PreviewRoomsToJoinViewModel
import com.example.picit.joinroom.UserRoomsScreen
import com.example.picit.joinroom.UserRoomsViewModel
import com.example.picit.login.LoginScreen
import com.example.picit.login.LoginViewModel
import com.example.picit.notifications.RoomInviteNotificationsScreen
import com.example.picit.picdesc.PromptRoomTakePicture
import com.example.picit.picdesc.PromptRoomVoteLeader
import com.example.picit.picdesc.PromptRoomVoteUserScreen
import com.example.picit.picdesc.SubmitPhotoDescriptionScreen
import com.example.picit.picdesc.WaitingPhotoDescriptionScreen
import com.example.picit.picdesccreateroom.ChooseGameScreen
import com.example.picit.picdesccreateroom.RoomSettingsScreen
import com.example.picit.createroom.picdesc.RoomTimeSettingsPicDescScreen
import com.example.picit.picdesccreateroom.RoomTimeSettingsRepicScreen
import com.example.picit.profile.UserProfileScreen
import com.example.picit.register.RegisterScreen
import com.example.picit.repic.RepicRoomPictureReleasedScreen
import com.example.picit.repic.RepicRoomTakePicture
import com.example.picit.repic.RepicRoomWaitingWinnerScreen
import com.example.picit.repic.RepicRoomWinnerScreen
import com.example.picit.settings.SettingsScreen
import com.example.picit.utils.DBUtils
import java.util.Calendar

private val TAG = "NavHost"

@Composable
fun PicItNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
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
        var currentUser by mutableStateOf(User())
        var currentRepicRoom by mutableStateOf(RePicRoom())
        var currentPicDescRoom by mutableStateOf(PicDescRoom())
        var dbutils = DBUtils()

        fun checkInterval(currentTime: Time, startTime: Time, endTime: Time): Boolean {
            return (currentTime.hours > startTime.hours || (currentTime.hours == startTime.hours && currentTime.minutes > startTime.minutes)) &&
                    (currentTime.hours < endTime.hours || currentTime.minutes < endTime.minutes )
        }

        composable(route= Screens.Login.route) {
            var loginViewModel : LoginViewModel = viewModel()
            val currentUserUpdate = { user: User -> currentUser = user }

            LoginScreen(
                onClickGoToRegistry = onClickGoToRegistry,
                onClickGoToMainScreen = onClickGoToMainScreen,
                currentUserUpdate = currentUserUpdate,
                viewModel = loginViewModel,
            )
        }
        composable(route= Screens.Register.route) {
            RegisterScreen(onClickBackButton = {onClickBackButton()}, onClickGoBackToLogin = onClickGoBackToLogin)
        }
        composable(route= Screens.Home.route) {
            val viewModel : UserRoomsViewModel = viewModel()
            viewModel.filterRoomsUserIsNotIn(currentUser.repicRooms, currentUser.picDescRooms)

            UserRoomsScreen(
                bottomNavigationsList= bottomNavigationsList,
                onClickJoinRoom = { navController.navigate(Screens.RoomsToJoin.route)},
                onClickCreateRoom = {navController.navigate(Screens.CreateRoomChooseGame.route)},
                onClickInvitesButton = {navController.navigate(Screens.InvitesNotifications.route)},
                onClickSettings = {navController.navigate(Screens.Settings.route)},
                onClickRoom = { roomId, gameType ->
                    val route = if (gameType == GameType.REPIC) {
                        Screens.RepicRoomScreen.route
                    } else {
                        Screens.PicDescRoomScreen.route
                    }
                    if(roomId != null) {
                        navController.navigate(route.replace("{room_id}", roomId))
                    }
                },
                userCurrentRepicRooms = viewModel.userRepicRooms,
                userCurrentPicDescRooms = viewModel.userPicdescRooms

            )
        }
        composable(route= Screens.Friends.route){
            FriendsListScreen(bottomNavigationsList= bottomNavigationsList)
        }
        composable(route = Screens.Profile.route){

            UserProfileScreen(
                bottomNavigationsList = bottomNavigationsList,
                name=currentUser.name,
                maxPoints = currentUser.maxPoints.toString(),
                numberOfWins = currentUser.totalWins.toString(),
                maxChallengeWinStreak = currentUser.maxWinStreak.toString(),
                nPhotosTaken = currentUser.nrPhotosTaken.toString()
            )
        }
        composable(route= Screens.RoomsToJoin.route){
            // TODO : TOU AQUI --------------------
            val previewRoomsToJoinViewModel: PreviewRoomsToJoinViewModel = viewModel()
            previewRoomsToJoinViewModel.filterRoomsUserIsIn(currentUser.repicRooms,currentUser.picDescRooms)
            val clickJoinRepicRoom = { roomId: String ->
                navController.navigate(
                    Screens.JoinRepicRoom.route.replace(
                        "{room_id}",
                        roomId
                    )
                )
            }
            val clickJoinPicdescRoom = {roomId: String ->
                navController.navigate(
                    Screens.JoinPicDescRoom.route.replace(
                        "{room_id}",
                        roomId
                    )
                )
            }

            PreviewRoomsToJoinScreen(
                repicRoomsAvailable = previewRoomsToJoinViewModel.repicRooms,
                picdescRoomsAvailable = previewRoomsToJoinViewModel.picdescRooms,
                onClickBackButton = {onClickBackButton()},
                clickJoinRepicRoom = clickJoinRepicRoom,
                clickJoinPicdescRoom = clickJoinPicdescRoom
            )
        }
        composable(
            route = Screens.JoinRepicRoom.route,
        ){ backStackEntry->
            val roomId = backStackEntry.arguments?.getString("room_id")!!
            val joinRepicRoomViewModel: JoinRepicRoomViewModel = viewModel()
            joinRepicRoomViewModel.loadRepicRoom(roomId)
            val room = joinRepicRoomViewModel.repicRoom

            val onClickJoinRoom = {
                joinRepicRoomViewModel.userJoinRoom(currentUser.id)
                joinRepicRoomViewModel.updateUserRepicRooms(currentUser)
                navController.navigate(Screens.Home.route)
            }
            JoinRepicRoomScreen(room.name, room.maxCapacity, room.currentCapacity,
                room.maxNumOfChallenges, room.currentNumOfChallengesDone,
                String.format("%02d", room.pictureReleaseTime.hours) + ":" + String.format("%02d", room.pictureReleaseTime.minutes),
                String.format("%02d", room.photoSubmissionOpeningTime.hours) + ":" + String.format("%02d", room.photoSubmissionOpeningTime.minutes),
                String.format("%02d", room.photoSubmissionClosingTime.hours) + ":" + String.format("%02d", room.photoSubmissionClosingTime.minutes),
                String.format("%02d", room.winnerAnnouncementTime.hours) + ":" + String.format("%02d", room.winnerAnnouncementTime.minutes),
                onClickJoinRoom, onClickBackButton = { onClickBackButton() })

        }
        composable(
            route = Screens.JoinPicDescRoom.route,
        ){ backStackEntry->
            val roomId = backStackEntry.arguments?.getString("room_id")!!
            val joinPicDescRoomViewModel: JoinPicDescRoomViewModel = viewModel()
            joinPicDescRoomViewModel.loadPicDescRoom(roomId)
            val room = joinPicDescRoomViewModel.picDescRoom

            val onClickJoinRoom = {
                joinPicDescRoomViewModel.updateUserPicDescRooms(currentUser)
                joinPicDescRoomViewModel.userJoinRoom(currentUser.id)
                navController.navigate(Screens.Home.route)
            }
            JoinPicDescRoomScreen(room.name, room.maxCapacity, room.currentCapacity,
                room.maxNumOfChallenges, room.currentNumOfChallengesDone,
                String.format("%02d", room.descriptionSubmissionOpeningTime.hours) + ":" + String.format("%02d", room.descriptionSubmissionOpeningTime.minutes),
                String.format("%02d", room.photoSubmissionOpeningTime.hours) + ":" + String.format("%02d", room.photoSubmissionOpeningTime.minutes),
                String.format("%02d", room.photoSubmissionOpeningTime.hours) + ":" + String.format("%02d", room.photoSubmissionOpeningTime.minutes),
                String.format("%02d", room.winnerAnnouncementTime.hours) + ":" + String.format("%02d", room.winnerAnnouncementTime.minutes),
                String.format("%02d", room.winnerAnnouncementTime.hours) + ":" + String.format("%02d", room.winnerAnnouncementTime.minutes),
                onClickJoinRoom, onClickBackButton = { onClickBackButton() })
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
                        name, capacity, numChallenges, privacy ->
                    navController.navigate(
                        route
                            .replace("{roomName}", name)
                            .replace("{capacity}", capacity)
                            .replace("{numChallenges}", numChallenges)
                            .replace("{privacy}", privacy)
                    )
                }
            )
        }
        composable(route = Screens.PicDescTimeSettings.route) { backStackEntry->
            val roomName = backStackEntry.arguments?.getString("roomName")
            val roomCapacity = backStackEntry.arguments?.getString("capacity")
            val numChallenges = backStackEntry.arguments?.getString("numChallenges")
            val privacyTokens = backStackEntry.arguments?.getString("privacy")?.split(":")
            val privacy = privacyTokens?.get(0).toBoolean()
            val privacyCode = privacyTokens?.get(1)


            if (roomName != null && roomCapacity != null && numChallenges != null && privacyCode != null) {
                RoomTimeSettingsPicDescScreen(
                    onClickBackButton = { onClickBackButton() },
                    roomName = roomName,
                    roomCapacity = roomCapacity,
                    numChallenges = numChallenges,
                    privacy = privacy,
                    privacyCode = privacyCode,
                    onClickGoHomeScreen = onClickGoToMainScreen,
                    currentUserId = currentUser.id,
                    currentUserRooms = currentUser.picDescRooms
                )
            }
        }
        composable(route = Screens.RePicTimeSettings.route){ backStackEntry->
            val roomName = backStackEntry.arguments?.getString("roomName")
            val roomCapacity = backStackEntry.arguments?.getString("capacity")
            val numChallenges = backStackEntry.arguments?.getString("numChallenges")
            val privacyTokens = backStackEntry.arguments?.getString("privacy")?.split(":")
            val privacy = privacyTokens?.get(0).toBoolean()
            val privacyCode = privacyTokens?.get(1)

            if (roomName != null && roomCapacity != null && numChallenges != null && privacyCode != null) {
                RoomTimeSettingsRepicScreen(
                    onClickBackButton = { onClickBackButton() },
                    roomName = roomName,
                    roomCapacity = roomCapacity,
                    numChallenges = numChallenges,
                    privacy = privacy,
                    privacyCode = privacyCode,
                    onClickGoHomeScreen = onClickGoToMainScreen,
                    currentUserId = currentUser.id,
                    currentUserRooms = currentUser.repicRooms
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
        composable(route = Screens.PicDescRoomScreen.route){ backStackEntry->
            val roomId = backStackEntry.arguments?.getString("room_id")
            if (roomId == null) return@composable
            dbutils.findPicDescRoomById(roomId, {room -> currentPicDescRoom = room})

            val currentCalendar = Calendar.getInstance() // TODO:  o tempo vai andando
            val currentTime = Time(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(Calendar.MINUTE))
            val currentUserIsLeader = currentUser.id == currentPicDescRoom.currentLeader

            val descriptionSubmissionOpeningTime = currentPicDescRoom.descriptionSubmissionOpeningTime
            val photoSubmissionOpeningTime = currentPicDescRoom.photoSubmissionOpeningTime
            val winnerAnnouncementTime = currentPicDescRoom.winnerAnnouncementTime

            if (checkInterval(currentTime,descriptionSubmissionOpeningTime,photoSubmissionOpeningTime)){
                if(currentUserIsLeader){
                    SubmitPhotoDescriptionScreen(
                        onClickBackButton = { onClickBackButton() }
                    )
                }
                else{
                    WaitingPhotoDescriptionScreen(
                        onClickBackButton = { onClickBackButton() }
                    )
                }
            }

            else if (checkInterval(currentTime,photoSubmissionOpeningTime,winnerAnnouncementTime)){
                if(currentUserIsLeader){
                    PromptRoomVoteLeader(
                        onClickBackButton = {onClickBackButton()}
                    )
                }
                else{
                    // check if user already submitted photo
                    val userAlreadySubmitted=
                        currentPicDescRoom.photosSubmitted.filter{ it.userId == currentUser.id}.size == 1

                    if(userAlreadySubmitted){
                        PromptRoomVoteUserScreen(
                            onClickBackButton = {onClickBackButton()}
                        )
                    }
                    else{
                        PromptRoomTakePicture(
                            onClickBackButton = {onClickBackButton()},
                            onClickCameraButton = {onClickCameraButton()},
                            room = currentPicDescRoom
                        )
                    }
                }
            }

        }
        composable(route = Screens.RepicRoomScreen.route){ backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("room_id")

            val currentCalendar = Calendar.getInstance() // TODO: o tempo vai andando
            val currentTime = Time(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(Calendar.MINUTE))

            if (roomId != null) {
                dbutils.findRepicRoomById(roomId, {room -> currentRepicRoom = room})

                val picReleaseTime = currentRepicRoom.pictureReleaseTime
                val submissionPicStartTime = currentRepicRoom.photoSubmissionOpeningTime
                val submissionPicEndTime = currentRepicRoom.photoSubmissionClosingTime
                val winnerTime = currentRepicRoom.winnerAnnouncementTime

                if (checkInterval(currentTime, picReleaseTime, submissionPicStartTime)) {
                    Log.w("TIME", "WAIT FOR PHOTO SUBMISSION START")
                    RepicRoomPictureReleasedScreen(onClickBackButton = { onClickBackButton() }, currentRepicRoom)
                } else if(checkInterval(currentTime, submissionPicStartTime, submissionPicEndTime)) {
                    Log.w("TIME", "SUBMIT PHOTO")
                    RepicRoomTakePicture(
                        onClickBackButton = { onClickBackButton() },
                        onClickCameraButton = onClickCameraButton,
                        currentRepicRoom
                    )
                } else if(checkInterval(currentTime, submissionPicEndTime, winnerTime)) {
                    Log.w("TIME", "WAITING FOR WINNER ANNOUNCEMENT")
                    RepicRoomWaitingWinnerScreen(onClickBackButton = { onClickBackButton() }, currentRepicRoom)
                } else {
                    Log.w("TIME", "WINNER ANNOUNCED")
                    RepicRoomWinnerScreen(onClickBackButton = { onClickBackButton() }, currentRepicRoom)
                }
            }
        }


    }

}




