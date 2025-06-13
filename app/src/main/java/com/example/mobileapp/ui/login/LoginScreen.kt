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
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import com.example.mobileapp.ui.sign_up.SignUpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object LoginDestination : NavigationDestination {
    override val route = "Login"
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToHomeScreen: () -> Unit,
    navigateToForgetPassword: () -> Unit,
    navigateToDialog: (String, String) -> Unit,
    onSignUpClick: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    signUpViewModel: SignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val loginState by loginViewModel.loginUiState.collectAsState()
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
                    userTyped = { loginViewModel.handleChange(loginState.loginDetails.copy(email = it)) },
                    label = stringResource(id = R.string.email),
                    icon = {
                        if (loginState.loginDetails.email.isNotEmpty()) {
                            IconAppBar(
                                icon = Icons.Default.Close,
                                onClick = { loginViewModel.clearEmailField() },
                                contentDescription = stringResource(id = R.string.email)
                            )
                        }
                    }
                )
                UserInput(
                    displayTyped = loginState.loginDetails.password,
                    userTyped = { loginViewModel.handleChange(loginState.loginDetails.copy(password = it)) },
                    label = stringResource(id = R.string.password),
                    visualTransformation = if(loginState.isIconPassClicked) VisualTransformation.None else PasswordVisualTransformation(),
                    icon = {
                        if(loginState.isIconPassClicked) {
                            IconAppBar(
                                icon = Icons.Default.Visibility,
                                onClick = { loginViewModel.showPassword() },
                                contentDescription = stringResource(id = R.string.password)
                            )
                        } else {
                            IconAppBar(
                                icon = Icons.Default.VisibilityOff,
                                onClick = { loginViewModel.showPassword() },
                                contentDescription = stringResource(id = R.string.password)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    modifier = Modifier
                        .clickable { navigateToForgetPassword() }
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
                Button(
                    onClick = {
                        loginViewModel.logInClicked()
                        coroutineScope.launch {
                            val isRegistered = loginViewModel.isEmailPassRegistered()

                            if (isRegistered) {
                                signUpViewModel.addLocalDB(loginState.loginDetails.email)
                                loginViewModel.userStatusLogIn(
                                    isUserLogIn = true,
                                    userEmail = loginState.loginDetails.email
                                )
                                withContext(Dispatchers.Main) {
                                    navigateToHomeScreen()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    loginViewModel.logInClicked()
                                    navigateToDialog(
                                        loginState.errorMessage,
                                        loginState.loginDetails.email
                                    )
                                }
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier
                        .height(40.dp)
                        .width(110.dp)
                ) {
                    if (loginState.isLogInClicked) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(24.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.login),
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(120.dp))
            SimpleButton(
                onClick = onSignUpClick,
                nameOfButton = stringResource(id = R.string.create_new_account),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .safeContentPadding()
            )
        }
    }
}