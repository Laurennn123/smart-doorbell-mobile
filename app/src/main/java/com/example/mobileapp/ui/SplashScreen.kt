package com.example.mobileapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mobileapp.ui.navigation.NavigationDestination
import com.example.mobileapp.ui.welcome.WelcomeDestination
import kotlinx.coroutines.delay

object SplashScreenDestination: NavigationDestination {
    override val route = "Splash Screen"
}

@Composable
fun SplashScreen(
    navigateToHomeScreen: () -> Unit,
    navigateToWelcomeScreen: () -> Unit,
    isUserLoggedIn: Boolean
) {
    LaunchedEffect(Unit) {
        if (isUserLoggedIn) {
            navigateToHomeScreen()
        } else {
            navigateToWelcomeScreen()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
