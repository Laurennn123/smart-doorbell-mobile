package com.example.mobileapp.ui.about_us

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mobileapp.BackAndUserAppBar
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.BottomNavigationBar
import com.example.mobileapp.ui.components.DefaultIcon
import com.example.mobileapp.ui.navigation.NavigationDestination

object AboutUsScreenDestination: NavigationDestination {
    override val route = "About us"
}

@Composable
fun AboutUsScreen(
    modifier: Modifier,
    navigateUp: () -> Unit,
    onClickBottomBar: (String) -> Unit,
    currentScreen: String,
    onClickAccount: () -> Unit
) {

    Scaffold(
        topBar = { BackAndUserAppBar(
            navigateUp = navigateUp,
            currentDestination = "About Us",
            onClickAccount = onClickAccount
        ) },
        bottomBar = {
            BottomNavigationBar(
                onClick = { onClickBottomBar(it) },
                currentScreen = currentScreen
            )
        }
    ) { innerPadding ->
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
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                Text(
                    text = stringResource(id = R.string.about_us),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.align(alignment = Alignment.Start)
                )
                Spacer(modifier = Modifier.height(100.dp))
                DefaultIcon(
                    icon = Icons.Default.Security,
                    nameOfIcon = "Our Logo",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.about_us_description),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}