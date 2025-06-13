package com.example.mobileapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.camera.view.LifecycleCameraController
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import com.example.mobileapp.model.AuthorizedPerson
import com.example.mobileapp.model.AuthorizedPersonModel
import com.example.mobileapp.model.EntriesHistoryModel
import com.example.mobileapp.ui.AuthorizedScreen
import com.example.mobileapp.ui.EntriesHistoryScreen
import com.example.mobileapp.ui.FaceEnrollmentScreen
import com.example.mobileapp.ui.account.MyAccountViewModel
import com.example.mobileapp.ui.camera.CameraPreviewScreen
import com.example.mobileapp.ui.camera.CameraViewModel
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.face_enrollment.FaceEnrollmentViewModel
import com.example.mobileapp.ui.navigation.SmartDoorBellNavHost


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
fun SmartEntryApp(
    context: Context,
    navController: NavHostController = rememberNavController()
) {
    SmartDoorBellNavHost(
        context = context,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackAndUserAppBar(
    navigateUp: () -> Unit,
    currentDestination: String,
    onClickAccount: () -> Unit,
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
            if (currentDestination == "About Us" || currentDestination == "Instructional Manual") {
                IconAppBar(
                    icon = Icons.Filled.AccountCircle,
                    onClick = onClickAccount,
                    contentDescription = stringResource(id = R.string.user_account),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAppBar(
    modifier: Modifier = Modifier,
    onClickSettings: () -> Unit,
    onClickAccount: () -> Unit,
    nameOwner: String,
) {
//    val userName = "Borils"
    TopAppBar(
        title = {
            Row {
                Column {
                    Text( text = stringResource(id = R.string.user_name, nameOwner) )
                    Text( text = stringResource(id = R.string.front_door))
                }
            }
        },
        actions = {
            Row {
                IconAppBar(
                    icon = Icons.Filled.Settings,
                    onClick = onClickSettings,
                    contentDescription = stringResource(id = R.string.menu),
                )
                IconAppBar(
                    icon = Icons.Filled.AccountCircle,
                    onClick = onClickAccount,
                    contentDescription = stringResource(id = R.string.user_account),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        modifier = modifier
    )
}