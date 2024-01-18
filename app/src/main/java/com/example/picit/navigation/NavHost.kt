package com.example.picit.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.picit.camera.CameraScreen
import com.example.picit.camera.PicDescCameraViewModel
import com.example.picit.camera.RePicCameraViewModel
import com.example.picit.createroom.picdesc.RoomTimeSettingsPicDescScreen
import com.example.picit.entities.GameType
import com.example.picit.entities.PicDescPhoto
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
import com.example.picit.leaderboard.LeaderboardScreen
import com.example.picit.leaderboard.LeaderboardViewModel
import com.example.picit.login.LoginScreen
import com.example.picit.login.LoginViewModel
import com.example.picit.notifications.RoomInviteNotificationsScreen
import com.example.picit.picdesc.PromptRoomTakePicture
import com.example.picit.picdesc.PromptRoomVoteLeader
import com.example.picit.picdesc.PromptRoomVoteLeaderViewModel
import com.example.picit.picdesc.PromptRoomVoteUserScreen
import com.example.picit.picdesc.PromptRoomVoteUserViewModel
import com.example.picit.picdesc.SubmitPhotoDescription
import com.example.picit.picdesc.SubmitPhotoDescriptionViewModel
import com.example.picit.picdesc.WaitingPhotoDescriptionScreen
import com.example.picit.picdesccreateroom.ChooseGameScreen
import com.example.picit.picdesccreateroom.RoomSettingsScreen
import com.example.picit.picdesccreateroom.RoomTimeSettingsRepicScreen
import com.example.picit.profile.UserProfileScreen
import com.example.picit.register.RegisterScreen
import com.example.picit.repic.RepicRoomTakePicture
import com.example.picit.repic.RepicRoomTakePictureViewModel
import com.example.picit.repic.RepicRoomWinnerScreen
import com.example.picit.settings.SettingsScreen
import com.example.picit.settings.SettingsViewModel
import com.example.picit.timer.TimerViewModel
import com.example.picit.utils.DBUtils
import com.example.picit.winner.Award
import com.example.picit.winner.DailyWinnerScreen
import com.example.picit.winner.DailyWinnerViewModel
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
        val onClickGoToRegistry = {navController.navigate(Screens.Register.route)}
        val onClickGoBackToLogin = {navController.navigate(Screens.Login.route)}
        val onClickGoToMainScreen = {navController.navigate(Screens.Home.route)}

        // done with id instead of the user, because updates with the user needed to be done with
        // listeners and that would have very complex logic
        var currentUser by mutableStateOf(User())
        var currentRepicRoom by mutableStateOf(RePicRoom())
        var currentPicDescRoom by mutableStateOf(PicDescRoom())
        // E tirar o listener quando se toca noutra sala
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

            Log.d(TAG, "Home Screen")

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
                userCurrentPicDescRooms = viewModel.userPicdescRooms,
                userID = currentUser.id
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
                joinRepicRoomViewModel.userJoinRoom(currentUser.id, currentUser.name)
                joinRepicRoomViewModel.updateUserRepicRooms(currentUser)
                navController.navigate(Screens.Home.route)
            }

            val onClickLeaderboard = {
                navController.navigate(Screens.LeaderboardScreen.route
                    .replace("{game_type}",GameType.REPIC.toString())
                    .replace("{room_id}", roomId))
            }

            JoinRepicRoomScreen(room.name, room.maxCapacity, room.currentCapacity,
                room.maxNumOfChallenges, room.currentNumOfChallengesDone,
                String.format("%02d", room.pictureReleaseTime.hours) + ":" + String.format("%02d", room.pictureReleaseTime.minutes),
                String.format("%02d", room.winnerAnnouncementTime.hours) + ":" + String.format("%02d", room.winnerAnnouncementTime.minutes),
                onClickJoinRoom, onClickBackButton = { onClickBackButton() }, onClickLeaderboard)

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
                joinPicDescRoomViewModel.userJoinRoom(currentUser.id, currentUser.name)
                navController.navigate(Screens.Home.route)
            }

            val onClickLeaderboard = {
                navController.navigate(Screens.LeaderboardScreen.route
                    .replace("{game_type}",GameType.PICDESC.toString())
                    .replace("{room_id}", roomId))
            }

            JoinPicDescRoomScreen(room.name, room.maxCapacity, room.currentCapacity,
                room.maxNumOfChallenges, room.currentNumOfChallengesDone,
                String.format("%02d", room.descriptionSubmissionOpeningTime.hours) + ":" + String.format("%02d", room.descriptionSubmissionOpeningTime.minutes),
                String.format("%02d", room.photoSubmissionOpeningTime.hours) + ":" + String.format("%02d", room.photoSubmissionOpeningTime.minutes),
                String.format("%02d", room.photoSubmissionOpeningTime.hours) + ":" + String.format("%02d", room.photoSubmissionOpeningTime.minutes),
                String.format("%02d", room.winnerAnnouncementTime.hours) + ":" + String.format("%02d", room.winnerAnnouncementTime.minutes),
                String.format("%02d", room.winnerAnnouncementTime.hours) + ":" + String.format("%02d", room.winnerAnnouncementTime.minutes),
                onClickJoinRoom, onClickBackButton = { onClickBackButton() }, onClickLeaderboard)
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
                    currentUserName = currentUser.username,
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
                    currentUserName = currentUser.username,
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

            var viewModel: SettingsViewModel = viewModel()

            SettingsScreen(
                onClickBackButton = {onClickBackButton()},
                viewModel = viewModel
            )
        }
        composable(route= Screens.PicDescCamera.route){
            val viewModel: PicDescCameraViewModel = viewModel()
            val context = LocalContext.current

            CameraScreen(
                onClickBackButton = {onClickBackButton()},
                getImageUri = { uri ->
                    viewModel.submitImage(currentPicDescRoom,currentUser,uri, context)
//                    navController.navigate(Screens.PicDescRoomScreen.route)
                }
            )
        }
        composable(route= Screens.RePicCamera.route){
            val viewModel: RePicCameraViewModel = viewModel()
            var context = LocalContext.current

            CameraScreen(
                onClickBackButton = {onClickBackButton()},
                getImageUri = { uri ->
                    viewModel.submitImage(currentRepicRoom,currentUser,uri, context)
//                    navController.navigate(Screens.RepicRoomScreen.route)
                }
            )
        }
        composable(route = Screens.PicDescRoomScreen.route){ backStackEntry->
            val roomId = backStackEntry.arguments?.getString("room_id") ?: return@composable

            if (!currentPicDescRoom.id.isNullOrEmpty()){
                dbutils.removePicDescListener(currentPicDescRoom.id!!)
            }

            val onClickLeaderboard = {
                navController.navigate(Screens.LeaderboardScreen.route
                    .replace("{game_type}",GameType.PICDESC.toString())
                    .replace("{room_id}", roomId))
            }

            dbutils.setPicDescRoomListener(roomId, {room -> currentPicDescRoom = room})

            val currentCalendar = Calendar.getInstance()
            val currentTime = Time(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(Calendar.MINUTE))
            val currentUserIsLeader = currentUser.id == currentPicDescRoom.currentLeader

            val descriptionSubmissionOpeningTime = currentPicDescRoom.descriptionSubmissionOpeningTime
            val photoSubmissionOpeningTime = currentPicDescRoom.photoSubmissionOpeningTime
            val winnerAnnouncementTime = currentPicDescRoom.winnerAnnouncementTime

            val viewModel: SubmitPhotoDescriptionViewModel = viewModel()
            val timerViewModel: TimerViewModel = viewModel()

            var didSeeWinnerScreen = false

            var dailyWinnerViewModel: DailyWinnerViewModel = viewModel()

            for (user in currentPicDescRoom.leaderboard) {
                if (user.userId == currentUser.id)
                    didSeeWinnerScreen = user.didSeeWinnerScreen
            }

            if (checkInterval(currentTime,descriptionSubmissionOpeningTime,photoSubmissionOpeningTime)
                || didSeeWinnerScreen) {

                if (checkInterval(currentTime,descriptionSubmissionOpeningTime,photoSubmissionOpeningTime)) {
                    dailyWinnerViewModel.setUserWinnerScreenVisibility(
                        currentPicDescRoom,
                        currentUser.id,
                        false
                    )
                }

                if(currentUserIsLeader){
                    SubmitPhotoDescription(
                        onClickBackButton = { onClickBackButton() },
                        onClickLeaderboard,
                        viewModel,
                        currentPicDescRoom
                    )
                }
                else{
                    WaitingPhotoDescriptionScreen(
                        onClickBackButton = { onClickGoToMainScreen() },
                        onClickLeaderboardButton = onClickLeaderboard,
                        roomName = currentPicDescRoom.name,
                        endingTime = currentPicDescRoom.photoSubmissionOpeningTime,
                        viewModel = timerViewModel
                    )
                }
            }
            // time to submit photo
            else if (checkInterval(currentTime,photoSubmissionOpeningTime,winnerAnnouncementTime)){
                val photosSubmitted = currentPicDescRoom.photosSubmitted
                val photosUserDidntVote = photosSubmitted.filter{
                    it.userId != currentUser.id && !it.usersThatVoted.contains(currentUser.id)
                }
                val photoDisplayed = if (photosUserDidntVote.isNotEmpty()) {photosUserDidntVote[0]} else PicDescPhoto()

                val viewModel: PromptRoomVoteLeaderViewModel = viewModel()
                if(currentUserIsLeader){
                    PromptRoomVoteLeader(
                        onClickBackButton = {onClickBackButton()},
                        onClickLeaderboardButton = onClickLeaderboard,
                        roomName = currentPicDescRoom.name,
                        photoDescription = currentPicDescRoom.photoDescription,
                        photo = photoDisplayed,
                        clickValidButton =  {
                            viewModel.leaderVote(photoDisplayed,currentUser,currentPicDescRoom,true)
                        },
                        clickInvalidButton = {
                            viewModel.leaderVote(photoDisplayed,currentUser,currentPicDescRoom,false)
                                             },
                        endingTime = currentPicDescRoom.winnerAnnouncementTime,
                        viewModel = timerViewModel
                    )
                }
                else{
                    val userAlreadySubmitted=
                        photosSubmitted.filter{ it.userId == currentUser.id}.size == 1

                    val viewModel: PromptRoomVoteUserViewModel = viewModel()

                    // check if user already submitted photo
                    if(userAlreadySubmitted){
                        PromptRoomVoteUserScreen(
                            onClickBackButton = {onClickBackButton()},
                            onClickLeaderboardButton = onClickLeaderboard,
                            roomName = currentPicDescRoom.name,
                            photoDescription = currentPicDescRoom.photoDescription,
                            endingTime = currentPicDescRoom.winnerAnnouncementTime,
                            viewModel = timerViewModel,
                            photo = photoDisplayed,
                            onClickRaitingStars = {raiting:Int ->
                                viewModel.userVote(currentUser,currentPicDescRoom,photoDisplayed,raiting)
                            }
                        )
                    }
                    else{
                        PromptRoomTakePicture(
                            onClickBackButton = {onClickBackButton()},
                            onClickLeaderboardButton = onClickLeaderboard,
                            room = currentPicDescRoom,
                            viewModel = timerViewModel,
                            onClickCameraButton = {navController.navigate(Screens.PicDescCamera.route)}
                        )
                    }
                }
            }
            // show winner
            else{

                var dailyWinnerViewModel: DailyWinnerViewModel = viewModel()
                var roomPhotos = currentPicDescRoom.photosSubmitted

                var fastestWinnerPhoto = PicDescPhoto()
                var mostVotedWinnerPhoto = PicDescPhoto()

                if (roomPhotos.isNotEmpty()) {
                    fastestWinnerPhoto = roomPhotos[0]
                    mostVotedWinnerPhoto = roomPhotos[0]
                }

                var currentMaxRating = 0.0

                for (photo in roomPhotos) {
                    if (photo.leaderVote) {
                        fastestWinnerPhoto = photo
                        break
                    }

                    var currentPhotoRating = photo.ratingSum / (photo.usersThatVoted.size - 1.0)

                    if (currentPhotoRating > currentMaxRating) {
                        currentMaxRating = currentPhotoRating
                        mostVotedWinnerPhoto = photo
                    }
                }

                dailyWinnerViewModel.setPicDescDesc(currentPicDescRoom.photoDescription)
                dailyWinnerViewModel.setFastestWinnerPhoto(fastestWinnerPhoto)
                dailyWinnerViewModel.setMostVotedWinnerPhoto(mostVotedWinnerPhoto)
                dailyWinnerViewModel.setCurrentAward(Award.FASTEST)

                DailyWinnerScreen(
                    gameType = GameType.PICDESC,
                    viewModel = dailyWinnerViewModel,
                    onClickRoom = {
                        if (currentPicDescRoom.currentNumOfChallengesDone < currentPicDescRoom.maxNumOfChallenges) {
                            dailyWinnerViewModel.incrementPlayersScores(currentPicDescRoom)

                            dailyWinnerViewModel.incrementDailyChallenges(currentPicDescRoom)

                            dailyWinnerViewModel.setUserWinnerScreenVisibility(currentPicDescRoom, currentUser.id, true)

                            if(currentPicDescRoom.id != null) {
                                navController.navigate(Screens.PicDescRoomScreen.route.replace("{room_id}", currentPicDescRoom.id!!))
                            }
                        }
                        else {
                            dailyWinnerViewModel.setUserWinnerScreenVisibility(currentPicDescRoom, currentUser.id, true)

                            navController.navigate(Screens.Home.route)
                        }
                    },
                    dailyChallenges = currentPicDescRoom.currentNumOfChallengesDone,
                    maxDailyChallenges = currentPicDescRoom.maxNumOfChallenges
                )

            }

        }
        composable(route = Screens.RepicRoomScreen.route){ backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("room_id") ?: return@composable
            var imageGenerated by remember { mutableStateOf(false) }

            if (!currentRepicRoom.id.isNullOrEmpty()){
                dbutils.removeRePicListener(currentRepicRoom.id!!)
            }
            dbutils.setRePicRoomListener(roomId, {room -> currentRepicRoom = room})

            val currentCalendar = Calendar.getInstance()
            val currentTime = Time(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(Calendar.MINUTE))

//            var viewModel: TimerViewModel = viewModel()

            val onClickLeaderboard = {
                navController.navigate(Screens.LeaderboardScreen.route
                    .replace("{game_type}",GameType.REPIC.toString())
                    .replace("{room_id}", roomId))
            }

            val picReleaseTime = currentRepicRoom.pictureReleaseTime
            val winnerTime = currentRepicRoom.winnerAnnouncementTime

            if (checkInterval(currentTime, picReleaseTime, winnerTime)) {
                Log.d("TIME", "SUBMIT PHOTO ")

                if(currentRepicRoom.imageUrl.isNullOrEmpty() && !imageGenerated){
                    Log.d(TAG,"Generating image")
                    val viewModelPicture: RepicRoomTakePictureViewModel = viewModel()
                    imageGenerated=true
                    viewModelPicture.getGeneratedImage(currentRepicRoom)
                }

                RepicRoomTakePicture(
                    onClickBackButton = { onClickBackButton() },
                    onClickCameraButton = {navController.navigate(Screens.RePicCamera.route)},
                    onClickLeaderboard,
                    viewModel = viewModel(),
                    currentRepicRoom
                )
            } else {
                Log.d("TIME", "WINNER ANNOUNCED")
                RepicRoomWinnerScreen(onClickBackButton = { onClickBackButton() }, onClickLeaderboard, currentRepicRoom)
            }
        }

        composable(route = Screens.LeaderboardScreen.route){ backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("room_id")
            val gameType = backStackEntry.arguments?.getString("game_type")
            val leaderboardViewModel : LeaderboardViewModel = viewModel()

            if (roomId != null) {
                if (gameType.equals(GameType.REPIC.toString())) {
                    dbutils.findRepicRoomById(roomId, {room -> currentRepicRoom = room})
                    leaderboardViewModel.getLeaderboardRepic(currentRepicRoom)
                    LeaderboardScreen(currentRepicRoom.name, leaderboardViewModel.usersInLeaderboard, {onClickBackButton()})
                }
                else {
                    dbutils.findPicDescRoomById(roomId, {room -> currentPicDescRoom = room})
                    leaderboardViewModel.getLeaderboardPicDesc(currentPicDescRoom)
                    LeaderboardScreen(currentPicDescRoom.name, leaderboardViewModel.usersInLeaderboard, {onClickBackButton()})
                }

            }
        }
    }
}




