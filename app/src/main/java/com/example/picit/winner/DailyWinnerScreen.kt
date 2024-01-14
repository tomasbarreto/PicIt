package com.example.picit.winner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.picit.R
import com.example.picit.entities.GameType
import com.example.picit.picdesc.Award
import com.example.picit.ui.theme.PicItTheme
import com.example.picit.utils.ScreenHeader

@Composable
fun DailyWinnerScreen(
    onClickBackButton: () -> Unit = {},
    award: Award,
    gameType: GameType,
    modifier: Modifier = Modifier,
    viewModel: DailyWinnerViewModel = viewModel()
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ScreenHeader(
            withBackButton = false,
            text = viewModel.getScreenTitle(),
            onClickBackButton = onClickBackButton
        )

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Outlined.AccountCircle, contentDescription = null, modifier = Modifier.size(160.dp))

            Text(text = viewModel.getUsername().uppercase(), textAlign = TextAlign.Center, fontSize = 25.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = modifier.height(30.dp))

            Row(
                modifier = modifier.width(270.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(Icons.Filled.LocationOn, contentDescription = null, modifier = Modifier.size(20.dp))

                    Spacer(modifier = modifier.width(3.dp))

                    Column {
                        Text(text = viewModel.getLocation(), textAlign = TextAlign.Center, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = modifier.height(3.dp))
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
                            text = viewModel.getTimeStamp(),
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = modifier.height(3.dp))
                    }
                }
            }

            Spacer(modifier = modifier.height(5.dp))

            // TO DO COLOCAR A IMAGEM
            Image(
                painter = painterResource(id = R.drawable.imagetorepic),
                contentDescription = "winner picture",
                modifier = modifier
                    .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
            )

            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = viewModel.getPicDescDescription(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                modifier = modifier.width(300.dp)
            )

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
                            text = viewModel.getRating(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(45.dp))

            Button(onClick = {  }) {
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