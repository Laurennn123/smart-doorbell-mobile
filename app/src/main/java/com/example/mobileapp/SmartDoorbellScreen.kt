package com.example.mobileapp

import android.graphics.LinearGradient
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import android.graphics.Shader
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.mobileapp.data.DataSource.settings
import com.example.mobileapp.model.AuthorizedPerson
import com.example.mobileapp.model.AuthorizedPersonModel
import com.example.mobileapp.model.EntriesHistory
import com.example.mobileapp.model.EntriesHistoryModel
import com.example.mobileapp.ui.AuthorizedScreen
import com.example.mobileapp.ui.EntriesHistoryScreen
import com.example.mobileapp.ui.FaceEnrollmentAddOrView
import com.example.mobileapp.ui.FaceEnrollmentScreen
import com.example.mobileapp.ui.HomeScreen
import com.example.mobileapp.ui.SettingsScreen
import com.example.mobileapp.ui.appbar.LogAndSignInBottomBar
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.login.LoginScreen
import com.example.mobileapp.ui.sign_up.SignUpScreen


enum class SmartDoorbellScreen {
    Home,
    Settings,
    Login,
    SignUp
}

enum class SettingsScreen {
    `Entries History`,
    `Face Enrollment`
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartDoorbellApp(
    authorizedViewModel: AuthorizedPersonModel = viewModel(),
    entriesViewModel: EntriesHistoryModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route
    var settingsSelect by rememberSaveable { mutableStateOf("") }

    Scaffold(
//        topBar = {
//            if (currentScreen == SmartDoorbellScreen.Home.name) {
//                HomeScreenAppBar()
//            } else {
//                BackAndUserAppBar(
//                    navigateUp = {
//                        if (currentScreen == "View Authorized" || currentScreen == "Add New") {
//                            navController.popBackStack(route = SmartDoorbellScreen.Settings.name, false)
//                        } else {
//                            navController.navigateUp()
//                        }
//                    }
//                )
//            }
//        },
//        bottomBar = {
//            LogAndSignInBottomBar()
//        },
    ) { innerPadding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(brush = Brush.linearGradient(
//                    colors = listOf(Color(0xFF041721), Color(0xFF00818F)
//                    )))
//        ) {
            val authorizeUiState by authorizedViewModel.uiState.collectAsState()
            val entriesUiState by entriesViewModel.uiState.collectAsState()

            NavHost(
                navController =  navController,
                startDestination = SmartDoorbellScreen.SignUp.name,
                modifier =  Modifier.padding(innerPadding)
            ) {
                composable(route = SmartDoorbellScreen.SignUp.name) {
                    SignUpScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .fillMaxSize()
                    )
                }

                composable(route = SmartDoorbellScreen.Login.name) {
                    LoginScreen(
                        signIn = { navController.navigate(SmartDoorbellScreen.Home.name) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = SmartDoorbellScreen.Home.name) {
                    HomeScreen(
                        onClick = {
                            navController.navigate(SmartDoorbellScreen.Settings.name)
                        },
                        tryClick = {
                            authorizeUiState.listOfAuthorizedPerson.add(
                                AuthorizedPerson(
                                    faceImage = R.drawable.thomas_si_boss,
                                    name = "Bossing",
                                    relationship = "Brother"
                                )
                            )
                            entriesViewModel.updateTimeAndDate()
                            entriesUiState.listOfEntries.add(
                                EntriesHistory(
                                    faceImage = R.drawable.thomas_si_boss,
                                    name = "Bossing",
                                    date = entriesViewModel.currentDate,
                                    time = entriesViewModel.currentTime
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    )
                }

                composable(route = SmartDoorbellScreen.Settings.name) {
                    SettingsScreen(
                        settings = settings,
                        onClick = { settingsSelect = it },
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    )
                    if (settingsSelect == SettingsScreen.`Face Enrollment`.name) {
                        FaceEnrollmentAddOrView(
                            onDismissRequest = {
                                settingsSelect = ""
                            },
                            onClickAddNew = {
                                settingsSelect = ""
                                navController.navigate("Add New")
                            },
                            onClickView = {
                                settingsSelect = ""
                                navController.navigate("View Authorized")
                            }
                        )
                    } else if (settingsSelect == SettingsScreen.`Entries History`.name) {
                        settingsSelect = ""
                        navController.navigate(SettingsScreen.`Entries History`.name)
                    }
                }

                composable(route = "Add New") {
                    FaceEnrollmentScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        onClick = {}
                    )
                }
                composable(route = "View Authorized") {
                    AuthorizedScreen(
                        listOfAllAuthorized = authorizeUiState.listOfAuthorizedPerson,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    )
                }

                composable(route = SettingsScreen.`Entries History`.name) {
                    EntriesHistoryScreen(
                        listOfEntries = entriesUiState.listOfEntries
                    )
                }

            }
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BackAndUserAppBar(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier) {
    TopAppBar(
        title = {},
        modifier = modifier,
        navigationIcon = {
            IconAppBar(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = navigateUp,
                contentDescription = stringResource(id = R.string.arrow_back),
            )
        },
        actions = {
            IconAppBar(
                icon = Icons.Filled.AccountCircle,
                onClick = { },
                contentDescription = stringResource(id = R.string.user_account),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF041721).copy(alpha = 0.1f)
        ),
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF041721).copy(alpha = 0.1f)
        ),
        modifier = modifier
    )


}