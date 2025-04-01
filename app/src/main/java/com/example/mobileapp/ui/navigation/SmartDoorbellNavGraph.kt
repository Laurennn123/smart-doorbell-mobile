package com.example.mobileapp.ui.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileapp.R
import com.example.mobileapp.ui.AppViewModelProvider
import com.example.mobileapp.ui.HomeScreen
import com.example.mobileapp.ui.HomeScreenDestination
import com.example.mobileapp.ui.SettingScreenDestination
import com.example.mobileapp.ui.SettingsScreen
import com.example.mobileapp.ui.login.LoginDestination
import com.example.mobileapp.ui.login.LoginScreen
import com.example.mobileapp.ui.login.LoginViewModel
import com.example.mobileapp.ui.sign_up.SignUpDestination
import com.example.mobileapp.ui.sign_up.SignUpScreen
import com.example.mobileapp.ui.welcome.WelcomeDestination
import com.example.mobileapp.ui.welcome.WelcomeScreen
import com.example.mobileapp.data.DataSource.settings
import com.example.mobileapp.model.HomeScreenModel
import com.example.mobileapp.ui.SplashScreen
import com.example.mobileapp.ui.SplashScreenDestination
import com.example.mobileapp.ui.about_us.AboutUsScreen
import com.example.mobileapp.ui.about_us.AboutUsScreenDestination
import com.example.mobileapp.ui.account.BirthDatePicker
import com.example.mobileapp.ui.account.MyAccountScreen
import com.example.mobileapp.ui.account.MyAccountScreenDestination
import com.example.mobileapp.ui.account.MyAccountViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SmartDoorBellNavHost(
    context: Context,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeScreenModel = viewModel(factory = AppViewModelProvider.Factory),
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    myAccountViewModel: MyAccountViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ) {

    val userSession = loginViewModel.userSession.collectAsState()
    val fullName by homeViewModel.fullName.collectAsState()
    val birthDate by myAccountViewModel.birthDateDb.collectAsState()

    var email by remember { mutableStateOf("") }
    var settingsOptionSelect by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = SplashScreenDestination.route) {
            SplashScreen(
                navigateToHomeScreen = { navController.navigate(route = HomeScreenDestination.route) {
                    popUpTo(0) { inclusive = true }
                } },
                navigateToWelcomeScreen = { navController.navigate(route = WelcomeDestination.route) },
                isUserLoggedIn = userSession.value.isUserLoggedIn
            )
        }

        composable(route = WelcomeDestination.route) {
            WelcomeScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .fillMaxSize(),
                onClickContinue = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(route = LoginDestination.route)
                },
            )
        }

        composable(route = LoginDestination.route) {
            LoginScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .statusBarsPadding(),
                navigateToHomeScreen = {
                    navController.navigate(route = HomeScreenDestination.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onSignUpClick = { navController.navigate(route = SignUpDestination.route) },
                navigateToDialog = { message, emailInput ->
                    errorMessage = message
                    email = emailInput
                }
            )
            if (errorMessage.isNotBlank()) {
                InvalidInputDialog(
                    message = errorMessage,
                    email = email,
                    onDismissRequest = { errorMessage = "" },
                    onCreateAccount = {
                        errorMessage = ""
                        navController.navigate(route = SignUpDestination.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        composable(route = SignUpDestination.route) {
            SignUpScreen(
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .statusBarsPadding()
            )
        }

        composable(route = HomeScreenDestination.route) {
            homeViewModel.setFullName(email = userSession.value.userEmail)
            HomeScreen(
                fullName = fullName,
                context = context,
                onClickSettings = { navController.navigate(route = SettingScreenDestination.route) },
                onClickAccount = { navController.navigate(route = MyAccountScreenDestination.route) } ,
                modifier =  Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            )
        }

        composable(route = SettingScreenDestination.route) {
            SettingsScreen(
                settings = settings,
                onClick = { settingsOptionSelect = it },
                navigateUp = { navController.navigateUp() },
                currentDestination = navController.currentDestination?.route.toString(),
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
            if (settingsOptionSelect == "About Us") {
                settingsOptionSelect = ""
                navController.navigate(route = AboutUsScreenDestination.route)
            } else if(settingsOptionSelect == "My Account") {
                settingsOptionSelect = ""
                navController.navigate(route = MyAccountScreenDestination.route)
            }
        }

        composable(route = AboutUsScreenDestination.route)  {
            AboutUsScreen(
                navigateUp = { navController.navigateUp() },
                onClickAccount = { navController.navigate(route = MyAccountScreenDestination.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }

        composable(route = MyAccountScreenDestination.route) {
            val TAG = "check"
            myAccountViewModel.setBirthDate(email = userSession.value.userEmail)
            Log.d(TAG, birthDate)
            MyAccountScreen(
                userName = "",
                email = userSession.value.userEmail,
                address = "",
                contactNumber = "",
                birthDate = birthDate,
                editClick = {  },
                addressClick = {  },
                contactClick = {  },
                dateClick = { myAccountViewModel.updateClicked(buttonClick = "Birth Date") },
                changePassClick = { },
                navigateUp = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
            if (myAccountViewModel.userButtonDetailClicked) {
                // Do the update query in here and get it 
                val buttonClicked = myAccountViewModel.buttonClicked
                if (buttonClicked == "Birth Date") {
                    val newBirthDate = BirthDatePicker(onDismissRequest = { myAccountViewModel.updateClicked(buttonClick = "") } )
                    if(newBirthDate.isNotBlank()) {
                        myAccountViewModel.updateBirthDate(dateBirth = newBirthDate, email = userSession.value.userEmail)
                        myAccountViewModel.updateClicked(buttonClick = "")
                    }
                }
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InvalidInputDialog(
    message: String,
    email: String,
    onDismissRequest: () -> Unit,
    onCreateAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            if (message == "Given String is empty or null" || message == "The email address is badly formatted.") {
                Text(text = "Enter your email, and password to log in.")
            } else if(message == "A network error (such as timeout, interrupted connection or unreachable host) has occurred.") {
                Text(text = "No internet connection")
            } else {
                Text(text = "Can't find account")
            }
        },
        text = {
            if (message == "Given String is empty or null" || message == "The email address is badly formatted.") {
                Text(text = "Either your email is badly formatted or no input both email and password")
            } else if(message == "A network error (such as timeout, interrupted connection or unreachable host) has occurred.") {
                Text(text = "Please connect to a Wifi or use data.")
            } else {
                Text(text = "We can't find an account with $email.")
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    if (message == "Given String is empty or null" || message == "The email address is badly formatted."
                        || message == "A network error (such as timeout, interrupted connection or unreachable host) has occurred.") {
                        onDismissRequest()
                    } else {
                        onCreateAccount()
                    }
                }
            ) {
                if (message == "Given String is empty or null" || message == "The email address is badly formatted." || message == "A network error (such as timeout, interrupted connection or unreachable host) has occurred.") {
                    Text(
                        text = "OK",
                        color = Color.Blue
                    )
                } else {
                    Text(
                        text = "SIGN UP",
                        color = Color.Blue
                    )
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    if (message == "Given String is empty or null" || message == "The email address is badly formatted.") {
                        onCreateAccount()
                    } else {
                        onDismissRequest()
                    }
                }
            ) {
                if (message == "Given String is empty or null" || message == "The email address is badly formatted.") {
                    Text(
                        text = "CREATE NEW ACCOUNT",
                        color = Color.Red
                    )
                } else if (message == "The supplied auth credential is incorrect, malformed or has expired.") {
                    Text(
                        text = "TRY AGAIN",
                        color = Color.Red
                    )
                }
            }
        },
        containerColor = Color.DarkGray,
        modifier = modifier
    )
}



