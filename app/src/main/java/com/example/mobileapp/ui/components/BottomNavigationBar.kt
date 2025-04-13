package com.example.mobileapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R

@Composable
fun BottomNavigationBar(
    onClick: (String) -> Unit,
    currentScreen: String
) {
    val navBottomBar = listOf(
        NavBottomBar(R.string.home_screen, if(currentScreen == "HomeScreen") Icons.Filled.Home else Icons.Outlined.Home),
        NavBottomBar(R.string.access_logs, if(currentScreen == "HomeScreen") Icons.Outlined.History else Icons.Filled.History)
    )

    var currentScreen2 by rememberSaveable { mutableStateOf("") }

    BottomAppBar(
        modifier = Modifier
            .background(Color.Transparent),
        containerColor = Color.Transparent,
        tonalElevation = 0.dp,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            navBottomBar.forEach { properties ->
                val nameOfIcon = stringResource(properties.title)
                if (currentScreen == "HomeScreen" && nameOfIcon == "Home"
                    || currentScreen == "" && nameOfIcon == "Access Logs") {
                    currentScreen2 = "HomeScreen"
                } else if (currentScreen == "HomeScreen" && nameOfIcon == "Access Logs"
                    || currentScreen == "" && nameOfIcon == "Home") {
                    currentScreen2 = ""
                }

                IconWithText(
                    icon = properties.icon,
                    nameOfIconButton = properties.title,
                    onClick = { onClick(nameOfIcon) },
                    currentScreen = currentScreen2,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }

}

@Composable
fun IconWithText(
    icon: ImageVector,
    onClick: () -> Unit,
    nameOfIconButton: Int,
    currentScreen: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        IconAppBar(
            icon = icon,
            onClick = onClick,
            contentDescription = stringResource(nameOfIconButton),
            color = if (currentScreen == "HomeScreen") Color.White else Color.LightGray,
            modifier = Modifier
                .size(29.dp),
            modifierIcon = Modifier
                .size(29.dp)
        )
        Text(
            text = stringResource(nameOfIconButton),
            color = if (currentScreen == "HomeScreen") Color.White else Color.LightGray,
            fontSize = 13.sp,
            modifier = Modifier
        )
    }
}

data class NavBottomBar(
    @StringRes val title: Int,
    val icon: ImageVector
)