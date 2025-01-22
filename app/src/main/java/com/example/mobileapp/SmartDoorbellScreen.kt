package com.example.mobileapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.data.DataSource.settings
import com.example.mobileapp.ui.FaceEnrollmentScreen
import com.example.mobileapp.ui.HomeScreen
import com.example.mobileapp.ui.SettingsScreen
import com.example.mobileapp.ui.components.IconAppBar


enum class SmartDoorbellScreen {
    Home,
    Settings,
    FaceEnrollment
}

@Composable
fun SmartDoorbellApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            if (backStackEntry?.destination?.route == SmartDoorbellScreen.Home.name) {
                HomeScreenAppBar()
            } else {
                BackAndUserAppBar()
            }
            // it will change if either login, register or already login
        }
    ) { innerPadding ->
        var settingsSelect by remember { mutableStateOf("") }

        NavHost(
            navController =  navController,
            startDestination = SmartDoorbellScreen.Home.name,
            modifier =  Modifier.padding(innerPadding)
        ) {

            composable(route = SmartDoorbellScreen.Home.name) {
                HomeScreen(
                    onClick = {
                        navController.navigate(SmartDoorbellScreen.Settings.name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = SmartDoorbellScreen.Settings.name) {
                SettingsScreen(
                    settings = settings,
                    onClick = {
                        settingsSelect = it
                        navController.navigate(settingsSelect)
                    },
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                )
            }

            composable(route = "Face Enrollment") {
                FaceEnrollmentScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    onClick = {
                    }
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BackAndUserAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = { },
        modifier = modifier,
        navigationIcon = {
            IconAppBar(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = {},
                contentDescription = stringResource(id = R.string.arrow_back),
                )
        },
        actions = {
            IconAppBar(
                icon = Icons.Filled.AccountCircle,
                onClick = {},
                contentDescription = stringResource(id = R.string.user_account),
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenAppBar(modifier: Modifier = Modifier) {
    val userName = "Borils"
    TopAppBar(
        title = {
            Row {
                Column {
                    Text( text = stringResource(id = R.string.user_name, userName) )
                    Text( text = stringResource(id = R.string.front_door))
                }
            }
        },
        actions = {
            Row {
                IconAppBar(
                    icon = Icons.Filled.Menu,
                    onClick = {},
                    contentDescription = stringResource(id = R.string.menu),
                )
                IconAppBar(
                    icon = Icons.Filled.AccountCircle,
                    onClick = {},
                    contentDescription = stringResource(id = R.string.user_account),
                )
            }
        },
        modifier = modifier
    )
}