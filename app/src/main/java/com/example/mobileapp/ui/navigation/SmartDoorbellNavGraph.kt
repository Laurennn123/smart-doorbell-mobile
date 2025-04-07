package com.example.mobileapp.ui.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileapp.ESP32Notification
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
import com.example.mobileapp.ui.account.InputDialog
import com.example.mobileapp.ui.account.MyAccountScreen
import com.example.mobileapp.ui.account.MyAccountScreenDestination
import com.example.mobileapp.ui.account.MyAccountViewModel
import com.example.mobileapp.ui.forget_password.ForgetPasswordDestination
import com.example.mobileapp.ui.forget_password.ForgetPasswordScreen
import com.example.mobileapp.ui.instructional_manual.InstructionalDestination
import com.example.mobileapp.ui.instructional_manual.InstructionalManualScreen
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val userSession = loginViewModel.userSession.collectAsState()
    val fullName by homeViewModel.fullName.collectAsState()
    val birthDate by myAccountViewModel.birthDateDb.collectAsState()
    val username by myAccountViewModel.userNameDb.collectAsState()
    val contactNumber by myAccountViewModel.contactNumberDb.collectAsState()
    val address by myAccountViewModel.addressDb.collectAsState()
    val password by myAccountViewModel.passwordDb.collectAsState()
    val profilePic by myAccountViewModel.profilePicDb.collectAsState()

    var email by remember { mutableStateOf("") }
    var settingsOptionSelect by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

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
                navigateToForgetPassword = {
                    navController.navigate(route = ForgetPasswordDestination.route)
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

        composable(route = ForgetPasswordDestination.route) {
            var errorMessageForgot by remember { mutableStateOf("") }
            ForgetPasswordScreen(
                onClickSend = {
                    errorMessageForgot = loginViewModel.sendPasswordResetEmail(it)
                    if (errorMessageForgot.isEmpty()) {
                        errorMessageForgot = "Successfully Email Send"
                    }
                },
                backToLoginClick = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(start = 45.dp, bottom = 60.dp)
            )
            if (errorMessageForgot == "Successfully Email Send") {
                SuccessMessageDialog(
                    message = "Successfully Email Send",
                    onClickContinue = {
                        errorMessageForgot = ""
                        navController.navigateUp()
                    }
                )
            } else if (errorMessageForgot == "Given String is empty or null" || errorMessageForgot == "The email address is badly formatted.") {
                InvalidEmailDialog(
                    message = errorMessageForgot,
                    onClickOk = {
                        errorMessageForgot = ""
                    }
                )
            }
        }

        composable(route = SignUpDestination.route) {
            var errorSignUpMessage by remember { mutableStateOf("") }
            val tag = "database"
            SignUpScreen(
                navigateBack = {
                    if (it == "") {
                        errorSignUpMessage = "Success Sign Up"
                    } else {
                        errorSignUpMessage = it
                    }
                },
                navigateToLogIn = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .statusBarsPadding()
            )
            Log.d(tag, errorSignUpMessage)
            if (errorSignUpMessage == "Success Sign Up") {
                SuccessMessageDialog(
                    message = "Success Sign Up",
                    onClickContinue = {
                        errorSignUpMessage = ""
                        navController.navigateUp()
                    }
                )
            } else if(
                errorSignUpMessage == "The email address is badly formatted." ||
                errorSignUpMessage == "Given String is empty or null" ||
                errorSignUpMessage == "The given password is invalid. [ Password should be at least 6 characters ]" ||
                errorSignUpMessage == "The email address is already in use by another account." ||
                errorSignUpMessage == "A network error (such as timeout, interrupted connection or unreachable host) has occurred." ||
                errorSignUpMessage == "Not equal password"
            )  {
                InvalidSignUpInputDialog(
                    message = errorSignUpMessage,
                    onClickOk = { errorSignUpMessage = "" }
                )
            }
        }

        composable(route = HomeScreenDestination.route) {
            Intent(context, ESP32Notification::class.java).also {
                it.action = ESP32Notification.Actions.START.toString()
                context.startForegroundService(it)
            }
            homeViewModel.setFullName(email = auth.currentUser?.email.toString())
            HomeScreen(
                fullName = fullName,
                context = context,
                onClickSettings = { navController.navigate(route = SettingScreenDestination.route) },
                onClickAccount = { navController.navigate(route = MyAccountScreenDestination.route) } ,
                modifier =  Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .verticalScroll(scrollState)
                    .imePadding(),
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
            } else if (settingsOptionSelect == "Instructional Manual") {
                settingsOptionSelect = ""
                navController.navigate(route = InstructionalDestination.route)
            } else if(settingsOptionSelect == "Log Out") {
                LogOutDialog(
                    onClickNo = { settingsOptionSelect = "" },
                    onClickYes = {
                        settingsOptionSelect = ""
                        auth.signOut()
                        CoroutineScope(Dispatchers.IO).launch {
                            loginViewModel.userStatusLogIn(isUserLogIn = false, userEmail = "")
                        }
                        Intent(context, ESP32Notification::class.java).also {
                            it.action = ESP32Notification.Actions.STOP.toString()
                            context.startForegroundService(it)
                        }
                        navController.navigate(route = LoginDestination.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(route = InstructionalDestination.route) {
            InstructionalManualScreen(
                navigateUp = { navController.navigateUp() },
                onClickAccount = { navController.navigate(route = MyAccountScreenDestination.route)},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
            )
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
            val coroutineScope = rememberCoroutineScope()
            myAccountViewModel.setEmail(email = userSession.value.userEmail)
            MyAccountScreen(
                userName = username,
                email = userSession.value.userEmail,
                address = address,
                contactNumber = contactNumber,
                birthDate = birthDate,
                editClick = { myAccountViewModel.updateClicked(buttonClick = "Username")  },
                addressClick = { myAccountViewModel.updateClicked(buttonClick = "Address") },
                contactClick = { myAccountViewModel.updateClicked(buttonClick = "Contact Number") },
                dateClick = { myAccountViewModel.updateClicked(buttonClick = "Birth Date") },
                changePassClick = { myAccountViewModel.updateClicked(buttonClick = "Password") },
                navigateUp = { navController.navigateUp() },
                uri = profilePic,
                updatePic = {
                    myAccountViewModel.updateProfilePic(uri = it, email = userSession.value.userEmail)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
            if (myAccountViewModel.userButtonDetailClicked) {
                val buttonClicked = myAccountViewModel.buttonClicked
                if (buttonClicked == "Birth Date") {
                    val newBirthDate = BirthDatePicker(onDismissRequest = { myAccountViewModel.updateClicked(buttonClick = "") } )
                    if(newBirthDate.isNotBlank()) {
                        myAccountViewModel.updateBirthDate(dateBirth = newBirthDate, email = userSession.value.userEmail)
                        myAccountViewModel.updateClicked(buttonClick = "")
                    }
                } else if (buttonClicked == "Address") {
                    InputDialog(
                        typeOfInput = stringResource(R.string.input_address),
                        nameOfInput = stringResource(R.string.address),
                        onDismissRequest = { myAccountViewModel.updateClicked(buttonClick = "")  },
                        valueInputted = myAccountViewModel.myAccountUiState.address,
                        onValueChange = {
                            myAccountViewModel.myAccountUiState = myAccountViewModel.myAccountUiState.copy(address = it)
                        },
                        cancelClick = { myAccountViewModel.updateClicked(buttonClick = "")  },
                        confirmClick = {
                            myAccountViewModel.updateAddress(address = myAccountViewModel.myAccountUiState.address, email = userSession.value.userEmail)
                            myAccountViewModel.myAccountUiState = myAccountViewModel.myAccountUiState.copy(address = "")
                            myAccountViewModel.updateClicked(buttonClick = "")
                        }
                    )
                } else if (buttonClicked == "Contact Number") {
                    InputDialog(
                        typeOfInput = stringResource(R.string.input_contact_num),
                        nameOfInput = stringResource(R.string.contact_num),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        onDismissRequest = { myAccountViewModel.updateClicked(buttonClick = "")  },
                        valueInputted = myAccountViewModel.myAccountUiState.contactNumber,
                        onValueChange = {
                            myAccountViewModel.myAccountUiState = myAccountViewModel.myAccountUiState.copy(contactNumber = it)
                        },
                        cancelClick = { myAccountViewModel.updateClicked(buttonClick = "")  },
                        confirmClick = {
                            myAccountViewModel.updateContactNumber(address = myAccountViewModel.myAccountUiState.contactNumber, email = userSession.value.userEmail)
                            myAccountViewModel.myAccountUiState = myAccountViewModel.myAccountUiState.copy(contactNumber = "")
                            myAccountViewModel.updateClicked(buttonClick = "")
                        }
                    )
                } else if (buttonClicked == "Username") {
                    InputDialog(
                        typeOfInput = stringResource(R.string.enter_message),
                        nameOfInput = stringResource(R.string.username),
                        onDismissRequest = { myAccountViewModel.updateClicked(buttonClick = "")  },
                        valueInputted = myAccountViewModel.myAccountUiState.userName,
                        onValueChange = {
                            myAccountViewModel.myAccountUiState = myAccountViewModel.myAccountUiState.copy(userName = it)
                        },
                        cancelClick = { myAccountViewModel.updateClicked(buttonClick = "")  },
                        confirmClick = {
                            myAccountViewModel.updateUsername(address = myAccountViewModel.myAccountUiState.userName, email = userSession.value.userEmail)
                            myAccountViewModel.myAccountUiState = myAccountViewModel.myAccountUiState.copy(userName = "")
                            myAccountViewModel.updateClicked(buttonClick = "")
                        }
                    )
                } else if (buttonClicked == "Password") {
                    InputDialog(
                        typeOfInput = stringResource(R.string.enter_password),
                        nameOfInput = stringResource(R.string.password),
                        onDismissRequest = { myAccountViewModel.updateClicked(buttonClick = "")  },
                        valueInputted = myAccountViewModel.myAccountUiState.password,
                        onValueChange = {
                            myAccountViewModel.myAccountUiState = myAccountViewModel.myAccountUiState.copy(password = it)
                        },
                        cancelClick = { myAccountViewModel.updateClicked(buttonClick = "")  },
                        confirmClick = {
                            myAccountViewModel.updatePassword(newPassword = myAccountViewModel.myAccountUiState.password, email = userSession.value.userEmail)
                            val tag = "password"
                            val newPassword = myAccountViewModel.myAccountUiState.password
                            val credential = EmailAuthProvider.getCredential(auth.currentUser?.email.toString(), password)
                            val user = auth.currentUser
                            coroutineScope.launch {
                                try {
                                    user?.reauthenticate(credential)?.await()
                                    user?.updatePassword(newPassword)
                                        ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val tag = "password"
                                            Log.d(tag, "User password updated.")
                                        }
                                    }?.await()
                                } catch (e: Exception) {
                                    Log.d(tag, FirebaseAuth.getInstance().currentUser?.email.toString())
                                    Log.d(tag, e.message.toString())
                                }
                            }
                            myAccountViewModel.myAccountUiState = myAccountViewModel.myAccountUiState.copy(password = "")
                            myAccountViewModel.updateClicked(buttonClick = "Password Updated")
                        }
                    )
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
                Text(text = "Either no account registered with $email or password is incorrect.")
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

@Composable
fun InvalidSignUpInputDialog(
    message: String,
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            if (message == "The email address is badly formatted.") {
                Text(text = "Your email is not formally formatted")
            } else if(message == "A network error (such as timeout, interrupted connection or unreachable host) has occurred.") {
                Text(text = "No internet connection")
            } else if(message == "Given String is empty or null") {
                Text(text = "The email or password is empty")
            } else if(message == "The given password is invalid. [ Password should be at least 6 characters ]") {
                Text(text = "Password invalid")
            } else if (message == "Not equal password") {
                Text(text = "Password and re-enter password should match")
            } else if (message == "The email address is already in use by another account.") {
                Text(text = "The email is already registered use another email")
            }
        },
        text = {
            if (message == "The email address is badly formatted.") {
                Text(text = "Enter your email in formally formatted with @")
            } else if(message == "A network error (such as timeout, interrupted connection or unreachable host) has occurred.") {
                Text(text = "Please connect to a Wifi or use data")
            } else if(message == "Given String is empty or null") {
                Text(text = "Please fill in the email or password")
            } else if(message == "The given password is invalid. [ Password should be at least 6 characters ]") {
                Text(text = "Please input at least 6 characters")
            } else if (message == "Not equal password") {
                Text(text = "Please input again with match password")
            }
        },
        onDismissRequest = { onClickOk() },
        confirmButton = {
            TextButton(
                onClick = onClickOk
            ) {
                Text(
                    text = "OK",
                    color = Color.White
                )
            }
        },
        containerColor = Color.DarkGray,
        modifier = modifier
    )
}

@Composable
fun LogOutDialog(
    onClickNo: () -> Unit,
    onClickYes: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = { Text("Are you sure do you want to log out?") },
        onDismissRequest = { onClickNo() },
        dismissButton = {
            TextButton(
                onClick = onClickNo
            ) {
                Text(
                    text = "NO",
                    color = Color.White
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onClickYes
            ) {
                Text(
                    text = "YES",
                    color = Color.Red
                )
            }
        },
        containerColor = Color.DarkGray,
        modifier = modifier
    )
}

@Composable
fun InvalidEmailDialog(
    message: String,
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            if(message == "Given String is empty or null") {
                Text("The email input field is empty")
            } else if (message == "The email address is badly formatted.") {
                Text("Your email is not formally formatted")
            }
        },
        onDismissRequest = onClickOk,
        confirmButton = {
            TextButton(
                onClick = onClickOk
            ) {
                Text(
                    text = "OK",
                    color = Color.White
                )
            }
        },
        containerColor = Color.DarkGray,
        modifier = modifier
    )
}

@Composable
fun SuccessMessageDialog(
    message: String,
    onClickContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            if(message == "Success Sign Up") {
                Text("Your account has been successfully created.")
            } else if (message == "Password Updated") {
                Text("Your password has been changed successfully.")
            } else if (message == "Successfully Email Send") {
                Text("Check your mailbox!")
            }
        },
        text = {
            if (message == "Successfully Email Send") {
                Text("We send you a link to reset your password.")
            }
        },
        onDismissRequest = { onClickContinue() },
        confirmButton = {
            TextButton(
                onClick = onClickContinue
            ) {
                Text(
                    text = "Continue",
                    color = Color.White
                )
            }
        },
        containerColor = Color.DarkGray,
        modifier = modifier
    )
}



