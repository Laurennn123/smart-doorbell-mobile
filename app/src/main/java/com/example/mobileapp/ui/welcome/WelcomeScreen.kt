package com.example.mobileapp.ui.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.DefaultIcon
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.navigation.NavigationDestination
import com.example.mobileapp.ui.theme.MobileAppTheme

object WelcomeDestination : NavigationDestination {
    override val route = "Welcome"
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onClickContinue: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(
                colors = listOf(Color(0xFF77C89D), Color(0xFF006663)
                )))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            DefaultIcon(
                icon = Icons.Default.Camera,
                nameOfIcon = "Our Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.welcome_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.welcome_motto),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(50.dp))
            SimpleButton(
                onClick = onClickContinue,
                nameOfButton = stringResource(id = R.string._continue),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
                    .offset(y = 100.dp),
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Test() {
    MobileAppTheme {
        WelcomeScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            onClickContinue = {}
        )
    }
}