package com.example.mobileapp.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.R
import com.example.mobileapp.ui.AppViewModelProvider
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.components.UserInput
import com.example.mobileapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

object LoginDestination : NavigationDestination {
    override val route = "Login"
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToHomeScreen: (String) -> Unit,
    onSignUpClick: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val loginState by loginViewModel.loginUiState.collectAsState()
    val userStatusState by loginViewModel.userState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF77C89D), Color(0xFF006663)
                    )
                )
            )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text(text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = dimensionResource(R.dimen.padding_medium))
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_large))
            ) {
                UserInput(
                    displayTyped = loginState.loginDetails.email,
                    userTyped = { loginViewModel.updateUi(loginState.loginDetails.copy(email = it)) },
                    label = stringResource(id = R.string.email)
                )
                UserInput(
                    displayTyped = loginState.loginDetails.password,
                    userTyped = { loginViewModel.updateUi(loginState.loginDetails.copy(password = it)) },
                    label = stringResource(id = R.string.password),
                    visualTransformation = if(loginViewModel.isIconPassClicked) VisualTransformation.None else PasswordVisualTransformation(),
                    icon = {
                        IconAppBar(
                            icon = Icons.Default.RemoveRedEye,
                            onClick = { loginViewModel.showPassword() },
                            contentDescription = stringResource(id = R.string.password)
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                loginViewModel.sendPasswordResetEmail()
                            }
                        }
                        .align(alignment = Alignment.End)
                        .drawBehind {
                            val strokeWidthPx = 1.dp.toPx()
                            val verticalOffset = size.height - 2.sp.toPx()
                            drawLine(
                                color = Color.LightGray,
                                strokeWidth = strokeWidthPx,
                                start = Offset(-1f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }
                )
                Spacer(modifier = Modifier.height(40.dp))
                SimpleButton(
                    onClick = {
                        loginViewModel.userStatusLogIn(isUserLogIn = !userStatusState.isUserLoggedIn)
                        coroutineScope.launch {
                            if (loginViewModel.isEmailPassRegistered()) {
                                launch { navigateToHomeScreen(loginViewModel.getFullName()) }
                            }
                        }
                    },
                    nameOfButton = stringResource(id = R.string.login).uppercase(),
                    shape = MaterialTheme.shapes.extraLarge,
                    enabled = loginState.isEntryValid
                )
            }
            Spacer(modifier = Modifier.height(120.dp))
            SimpleButton(
                onClick = onSignUpClick,
                nameOfButton = stringResource(id = R.string.sign_up).uppercase(),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


