package com.example.mobileapp.ui.access_logs_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.mobileapp.HomeScreenAppBar
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.BottomNavigationBar
import com.example.mobileapp.ui.navigation.NavigationDestination

object AccessLogsDestination : NavigationDestination {
    override val route = "Access Logs"
}

@Composable
fun AccessLogsScreen(
    onClickBottomBar: (String) -> Unit,
    currentScreen: String,
    modifier: Modifier = Modifier
) {

    Scaffold(
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
                    text = stringResource(id = R.string.access_logs),
                    style = MaterialTheme.typography.displayLarge,
                )

            }

        }
    }

}