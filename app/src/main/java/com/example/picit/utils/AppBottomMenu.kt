package com.example.picit.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

@Composable
fun AppBottomMenu(selectedItem: Int, onClickForItems: List<()->Unit>) {
    val contentColor = Color(255,255,250)
    val items = listOf("Friends", "Home", "Profile")
    val iconsFilled = listOf(Icons.Filled.Person,Icons.Filled.Home, Icons.Filled.AccountCircle)
    val iconsOutlined = listOf(Icons.Outlined.Person,Icons.Outlined.Home, Icons.Outlined.AccountCircle)
    BottomNavigation(
        backgroundColor = Color(13,92,99),
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    if(selectedItem == index)
                        Icon(iconsFilled[index], contentDescription = null,
                            tint = contentColor)
                    else
                        Icon(iconsOutlined[index], contentDescription = null,
                            tint = contentColor)
                       },
                label = { Text(item, color = contentColor) },
                selected = selectedItem == index,
                onClick = onClickForItems[index]
            )
        }
    }
}