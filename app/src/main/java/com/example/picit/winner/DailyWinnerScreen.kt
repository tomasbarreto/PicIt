package com.example.picit.winner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.picit.R
import com.example.picit.entities.GameType
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@Composable
fun DailyWinnerScreen(
    gameType: GameType,
    modifier: Modifier = Modifier,
    viewModel: DailyWinnerViewModel = viewModel(),
    onClickRoom: () -> Unit = {},
    dailyChallenges: Int = 0,
    maxDailyChallenges: Int = 0
) {

    var timestamp = if (gameType == GameType.PICDESC) {
        viewModel.shownPicDescWinnerPhoto.submissionTime.hours.toString() + ":" + viewModel.shownPicDescWinnerPhoto.submissionTime.minutes.toString()
    }
    else {
        viewModel.shownRePicWinnerPhoto.submissionTime.hours.toString() + ":" + viewModel.shownRePicWinnerPhoto.submissionTime.minutes.toString()
    }

    var rating = if (viewModel.shownPicDescWinnerPhoto.usersThatVoted.size - 1 > 1) {
        String.format("%.1f",
                viewModel.shownPicDescWinnerPhoto.ratingSum / (viewModel.shownPicDescWinnerPhoto.usersThatVoted.size - 1.0)
        )
    } else {
        "0.0"
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            withBackButton = false,
            text = viewModel.screenTitle,
        )

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Outlined.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(160.dp)
            )

            Text(
                text = viewModel.shownPicDescWinnerPhoto.username.uppercase(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = modifier.height(30.dp))

            var hasLocation = if (gameType == GameType.PICDESC)
                viewModel.shownPicDescWinnerPhoto.location.isNotEmpty()
            else
                viewModel.shownRePicWinnerPhoto.location.isNotEmpty()

            var arrangement = Arrangement.End

            if (hasLocation) {
                arrangement = Arrangement.SpaceBetween
            }

            Row(
                modifier = modifier.width(270.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = arrangement
            ) {
                if (hasLocation) {
                    Row {
                        Icon(
                            Icons.Filled.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = modifier.width(3.dp))

                        Column {
                            if (gameType == GameType.PICDESC) {
                                Text(
                                    text = viewModel.shownPicDescWinnerPhoto.location,
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            else {
                                Text(
                                    text = viewModel.shownRePicWinnerPhoto.location,
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = modifier.height(3.dp))
                        }
                    }
                }

                Row {
                    Image(
                        painter = painterResource(id = R.drawable.clock2),
                        contentDescription = "picture timestamp",
                        modifier = modifier.size(20.dp)
                    )

                    Spacer(modifier = modifier.width(3.dp))

                    Column {
                        Text(
                            text = timestamp,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = modifier.height(3.dp))
                    }
                }
            }

            Spacer(modifier = modifier.height(5.dp))

            if (gameType == GameType.PICDESC) {
                AsyncImage(
                    model = viewModel.shownPicDescWinnerPhoto.photoUrl,
                    contentDescription = viewModel.picDescDescription,
                    modifier = Modifier
                        .fillMaxHeight(0.45f)
                        .fillMaxWidth(0.8f)
                        .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
                )
            }
            else {
                AsyncImage(
                    model = viewModel.shownRePicWinnerPhoto.photoUrl,
                    contentDescription = viewModel.picDescDescription,
                    modifier = Modifier
                        .fillMaxHeight(0.45f)
                        .fillMaxWidth(0.8f)
                        .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            if (gameType == GameType.PICDESC) {
                Text(
                    text = viewModel.picDescDescription,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    modifier = modifier.width(300.dp)
                )
            }
            else {
                Text(
                    text = viewModel.rePicDescription,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    modifier = modifier.width(300.dp)
                )
            }

            if (gameType == GameType.PICDESC) {
                Spacer(modifier = modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.estrela2),
                        contentDescription = "star rating",
                        modifier = Modifier.size(25.dp)
                    )

                    Spacer(modifier = modifier.width(3.dp))

                    Column {
                        Text(
                            text = rating,
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(45.dp))

            Button(onClick = {
                when (viewModel.getCurrentAward()) {
                    Award.FASTEST -> viewModel.setCurrentAward(Award.MOST_VOTED)
                    Award.MOST_VOTED -> {
                        if (dailyChallenges < maxDailyChallenges)
                            onClickRoom()
                        else
                            viewModel.setCurrentAward(Award.REPIC_WINNER)
                    }
                    Award.REPIC_WINNER -> onClickRoom()
                }
            }) {
                Text(text = "Continue", fontSize = 22.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyWinnerPreview() {
    PicItTheme {
        //DailyWinnerScreen()
    }
}