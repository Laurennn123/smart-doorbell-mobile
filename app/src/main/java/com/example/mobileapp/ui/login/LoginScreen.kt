package com.example.mobileapp.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.components.UserInput

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(start = dimensionResource(R.dimen.padding_medium))
        )
        Spacer(modifier = Modifier.height(120.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_large))
        ) {
            UserInput(
                displayTyped = "",
                userTyped = {},
                label = stringResource(id = R.string.email)
            )
            UserInput(
                displayTyped = "",
                userTyped = {},
                label = stringResource(id = R.string.password),
                visualTransformation = PasswordVisualTransformation(),
                icon = {
                    IconAppBar(
                        icon = Icons.Default.RemoveRedEye,
                        onClick = {},
                        contentDescription = stringResource(id = R.string.password)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.forgot_password),
                modifier = Modifier.clickable {

                }.align(alignment = Alignment.End)
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
        }
        Spacer(modifier = Modifier.height(70.dp))
        SimpleButton(
            onClick = onLoginClick,
            nameOfButton = stringResource(id = R.string.login).uppercase(),
            shape = MaterialTheme.shapes.extraLarge,
        )
    }

}


