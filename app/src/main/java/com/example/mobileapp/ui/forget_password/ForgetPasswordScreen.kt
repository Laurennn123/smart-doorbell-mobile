package com.example.mobileapp.ui.forget_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.components.UserInput
import com.example.mobileapp.ui.navigation.NavigationDestination

object ForgetPasswordDestination : NavigationDestination {
    override val route = "Forget Password"
}

@Composable
fun ForgetPasswordScreen(
    onClickSend: (String) -> Unit,
    backToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var userInput by remember { mutableStateOf("") }

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
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password_title),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
            )
            Text(
                text = stringResource(id = R.string.enter_your_email),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(30.dp))
            UserInput(
                displayTyped = userInput,
                userTyped = { userInput = it },
                label = stringResource(id = R.string.email),
                icon = {
                    if (userInput.isNotEmpty()) {
                        IconAppBar(
                            icon = Icons.Default.Close,
                            onClick = { userInput = "" },
                            contentDescription = stringResource(id = R.string.email)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SimpleButton(
                    onClick = { onClickSend(userInput) },
                    nameOfButton = stringResource(R.string.send),
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier
                        .padding(end = 60.dp)
                )
                Spacer(modifier = Modifier.height(400.dp))
                SimpleButton(
                    onClick = backToLoginClick,
                    nameOfButton = stringResource(id = R.string.login),
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 60.dp)
                )
            }
        }
    }

}