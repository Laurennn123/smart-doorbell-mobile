package com.example.mobileapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileapp.R
import com.example.mobileapp.ui.HomeScreen
import com.example.mobileapp.ui.HomeScreenDestination
import com.example.mobileapp.ui.login.LoginDestination
import com.example.mobileapp.ui.login.LoginScreen
import com.example.mobileapp.ui.sign_up.SignUpDestination
import com.example.mobileapp.ui.sign_up.SignUpScreen
import com.example.mobileapp.ui.welcome.WelcomeDestination
import com.example.mobileapp.ui.welcome.WelcomeScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun SmartDoorBellNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier) {

    var nameOwner: String = remember { mutableStateOf("").toString() }

    NavHost(
        navController = navController,
        startDestination = WelcomeDestination.route,
        modifier = modifier
    ) {
        composable(route = WelcomeDestination.route) {
            WelcomeScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .fillMaxSize(),
                onClickContinue = {
                    navController.popBackStack()
                    navController.navigate(route = LoginDestination.route)
                }
            )
        }

        composable(route = LoginDestination.route) {
            LoginScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .statusBarsPadding(),
                navigateToHomeScreen = {
                    nameOwner = it
                    navController.popBackStack()
                    navController.navigate(route = HomeScreenDestination.route,)
                },
                onSignUpClick = { navController.navigate(route = SignUpDestination.route) }
            )
        }

        composable(route = SignUpDestination.route) {
            SignUpScreen(
                navigateBack = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .statusBarsPadding()
            )
        }

        composable(route = HomeScreenDestination.route) {
            HomeScreen(
                nameOwner = nameOwner,
                tryClick = {},
                onClickEnter = {},
                modifier =  Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            )
        }

    }
}

