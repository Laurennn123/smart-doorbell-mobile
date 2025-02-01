package com.example.mobileapp.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.DefaultIcon
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.components.TextFieldWithIcon
import com.example.mobileapp.ui.components.WelcomeMessage

@Composable
fun LoginScreen(
    signIn: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        WelcomeMessage()
        Spacer(modifier = Modifier.height(40.dp))
        Username()
        Password()
        Spacer(modifier = Modifier.height(20.dp))
        SignIn(onClick = signIn)
    }
}

@Composable
private fun Username() {
    TextFieldWithIcon(
        icon = { DefaultIcon(
            icon = Icons.Outlined.AccountCircle,
            nameOfIcon = stringResource(R.string.username)) },
        label = stringResource(R.string.username),
        displayTyped = "",
        userTyped = {},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun Password() {
    TextFieldWithIcon(
        icon = { DefaultIcon(
            icon = Icons.Outlined.Lock,
            nameOfIcon = stringResource(R.string.password)) },
        label = stringResource(R.string.password),
        displayTyped = "",
        userTyped = {},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun SignIn(
    onClick: () -> Unit
) {
    SimpleButton(
        onClick = onClick,
        nameOfButton = stringResource(R.string.sign_in),
        shape = MaterialTheme.shapes.extraLarge
    )
}