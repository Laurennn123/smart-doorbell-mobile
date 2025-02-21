package com.example.mobileapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.LinearGradient
import android.graphics.Matrix
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
import android.util.Log
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
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
import com.example.mobileapp.ui.about_us.AboutUsScreen
import com.example.mobileapp.ui.account.BirthDatePicker
import com.example.mobileapp.ui.account.InputDialog
import com.example.mobileapp.ui.account.MyAccountScreen
import com.example.mobileapp.ui.account.MyAccountViewModel
import com.example.mobileapp.ui.appbar.LogAndSignInBottomBar
import com.example.mobileapp.ui.camera.CameraPreviewScreen
import com.example.mobileapp.ui.camera.CameraViewModel
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.face_enrollment.FaceEnrollmentViewModel
import com.example.mobileapp.ui.login.LoginScreen
import com.example.mobileapp.ui.sign_up.SignUpScreen
import com.example.mobileapp.ui.welcome.WelcomeScreen
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


enum class SmartDoorbellScreen {
    Home,
    Settings,
    Login,
    SignUp,
    Welcome,
    AboutUs,
    Account,
}

enum class SettingsScreen {
    `Entries History`,
    `Face Enrollment`,
    `About Us`,
    `My Account`
}

@Composable
fun SmartDoorbellApp(
    controller: LifecycleCameraController,
    context: Context,
    authorizedViewModel: AuthorizedPersonModel = viewModel(),
    entriesViewModel: EntriesHistoryModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    myAccountModel: MyAccountViewModel = viewModel(),
    cameraModel: CameraViewModel = viewModel(),
    faceEnrollmentModel: FaceEnrollmentViewModel = viewModel()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val accountUiState by myAccountModel.accountUiState.collectAsState()
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
                startDestination = SmartDoorbellScreen.Home.name,
                modifier =  Modifier.padding(innerPadding)
            ) {
                composable(route = SmartDoorbellScreen.Welcome.name) {
                    WelcomeScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                            .fillMaxSize(),
                        onClickContinue = {
                            navController.navigate(route = SmartDoorbellScreen.Login.name)
                        }
                    )
                }

                composable(route = SettingsScreen.`My Account`.name) {
                    MyAccountScreen(
                        userName = accountUiState.userName,
                        email = accountUiState.email,
                        address = accountUiState.address,
                        contactNumber = accountUiState.contactNumber,
                        birthDate = accountUiState.birthDate,
                        editClick = { myAccountModel.updateClicked(buttonClick = "Edit") },
                        addressClick = { myAccountModel.updateClicked(buttonClick = "Address") },
                        contactClick = { myAccountModel.updateClicked(buttonClick = "Contact") },
                        dateClick = { myAccountModel.updateClicked(buttonClick = "Birth Date")},
                        changePassClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                            .statusBarsPadding()
                    )
                    if(myAccountModel.userButtonDetailClicked) {
                        val buttonClicked = myAccountModel.buttonClicked
                        if (buttonClicked == "Address") {
                            InputDialog(
                                typeOfInput = stringResource(R.string.input_address),
                                nameOfInput = stringResource(R.string.address),
                                onDismissRequest = { myAccountModel.onDismiss() },
                                valueInputted = myAccountModel.address,
                                onValueChange = { myAccountModel.address = it},
                                cancelClick = { myAccountModel.onDismiss() },
                                confirmClick = { myAccountModel.updateAddress() }
                            )
                        } else if (buttonClicked == "Contact") {
                            InputDialog(
                                typeOfInput = stringResource(R.string.input_contact_num),
                                nameOfInput = stringResource(R.string.contact_num),
                                onDismissRequest = { myAccountModel.onDismiss() },
                                valueInputted = myAccountModel.contactNumber,
                                onValueChange = { myAccountModel.contactNumber = it  },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal,
                                    imeAction = ImeAction.Done
                                ),
                                cancelClick = { myAccountModel.onDismiss() },
                                confirmClick = { myAccountModel.updateContactNumber() }
                            )
                        } else if (buttonClicked == "Edit") {
                            InputDialog(
                                typeOfInput = stringResource(R.string.enter_username),
                                nameOfInput = stringResource(R.string.username),
                                onDismissRequest = { myAccountModel.onDismiss() },
                                valueInputted = myAccountModel.userName,
                                onValueChange = {
                                    if (myAccountModel.userName.length <= 10) {
                                        myAccountModel.userName = it
                                    } else {
                                        myAccountModel.userName = myAccountModel.userName.slice(0..9)
                                    }
                                },
                                cancelClick = { myAccountModel.onDismiss() },
                                confirmClick = { myAccountModel.updateUsername() }
                            )
                        } else if (buttonClicked == "Birth Date") {
                            val birthDate =   BirthDatePicker(onDismissRequest = { myAccountModel.onDismiss() })
                            if(birthDate.isNotBlank()) {
                                myAccountModel.updateBirthDate(birthDate)
                            }
                        }
                    }
                }

                composable(route = SmartDoorbellScreen.Login.name) {
                    LoginScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .statusBarsPadding(),
                        onLoginClick = {
                            navController.navigate(route = SmartDoorbellScreen.AboutUs.name)
                        }
                    )
                }

                composable(route = SmartDoorbellScreen.SignUp.name) {
                    SignUpScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .fillMaxSize()
                    )
                }

                composable(route = SettingsScreen.`About Us`.name) {
                    AboutUsScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .fillMaxSize()
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    )
                }

                composable(route = SmartDoorbellScreen.Home.name) {

                    HomeScreen(
                        onClick = {
                            navController.navigate(SmartDoorbellScreen.Settings.name)
                        },
                        tryClick = {
//                            authorizeUiState.listOfAuthorizedPerson.add(
//                                AuthorizedPerson(
//                                    faceImage = R.drawable.thomas_si_boss,
//                                    name = "Bossing",
//                                    relationship = "Brother"
//                                )
//                            )
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
                    } else if (settingsSelect == SettingsScreen.`About Us`.name){
                        settingsSelect = ""
                        navController.navigate(SettingsScreen.`About Us`.name)
                    } else if(settingsSelect == SettingsScreen.`My Account`.name) {
                        settingsSelect = ""
                        navController.navigate(SettingsScreen.`My Account`.name)
                    }
                }

                composable(route = "Add New") {
                    FaceEnrollmentScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        onClick = {
                            navController.navigate("Camera Screen")
                        },
                        photoTaken = cameraModel.picture,
                        addPerson = {
                            authorizeUiState.listOfAuthorizedPerson.add(
                                AuthorizedPerson(
                                    faceImage = cameraModel.picture,
                                    name = faceEnrollmentModel.faceUiState.authorizedPerson.name,
                                    relationship = faceEnrollmentModel.faceUiState.authorizedPerson.relationship
                                )
                            )
                            cameraModel.removePic()
                            faceEnrollmentModel.reset()
                            navController.navigateUp()
                        },
                        name = faceEnrollmentModel.faceUiState.authorizedPerson.name,
                        relationship = faceEnrollmentModel.faceUiState.authorizedPerson.relationship,
                        faceEnrollment = faceEnrollmentModel,
                        onType = faceEnrollmentModel::updatePersonDetails,
                    )
                }

                composable(route = "Camera Screen") {
                    CameraPreviewScreen(
                        modifier = Modifier.fillMaxSize(),
                        controller = controller,
                        onClickPhoto = {
                            cameraModel.takePhoto(
                                controller = controller,
                                context = context,
                            )
                        },
                        navigateUp = {
                            navController.navigateUp()
                        },
                        photoTaken = cameraModel.picture
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