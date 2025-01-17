package com.example.mobileapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.data.DataSource.settings
import com.example.mobileapp.ui.SettingsScreen
import com.example.mobileapp.ui.components.IconAppBar

enum class SmartDoorbellScreen {
    Home
}

@Composable
fun SmartDoorbellApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            BackAndUserAppBar()
        }
    ) { innerPadding ->
        SettingsScreen(
            settings = settings,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BackAndUserAppBar(modifier: Modifier = Modifier){
    TopAppBar(
        title = { },
        modifier = modifier,
        navigationIcon = {
            IconAppBar(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = {},
                contentDescription = "Navigation",
                )
        },
        actions = {
            IconAppBar(
                icon = Icons.Filled.AccountCircle,
                onClick = {},
                contentDescription = "User Account",
            )
        }
    )
}