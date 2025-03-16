package com.example.mobileapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileapp.R
import com.example.mobileapp.SmartDoorbellScreen
import com.example.mobileapp.ui.login.LoginDestination
import com.example.mobileapp.ui.login.LoginScreen
import com.example.mobileapp.ui.sign_up.SignUpDestination
import com.example.mobileapp.ui.sign_up.SignUpScreen
import com.example.mobileapp.ui.welcome.WelcomeDestination
import com.example.mobileapp.ui.welcome.WelcomeScreen

@Composable
fun SmartDoorBellNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = SignUpDestination.route,
        modifier = modifier
    ) {
        composable(route = WelcomeDestination.route) {
            WelcomeScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .fillMaxSize(),
                onClickContinue = { navController.navigate(route = LoginDestination.route) }
            )
        }

        composable(route = LoginDestination.route) {
            LoginScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .statusBarsPadding(),
                onLoginClick = { },
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

    }
}

