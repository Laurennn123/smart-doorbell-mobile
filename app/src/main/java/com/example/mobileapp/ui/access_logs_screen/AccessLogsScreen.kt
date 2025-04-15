package com.example.mobileapp.ui.access_logs_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.R
import com.example.mobileapp.data.table.AccessLogs
import com.example.mobileapp.ui.AppViewModelProvider
import com.example.mobileapp.ui.components.BottomNavigationBar
import com.example.mobileapp.ui.navigation.NavigationDestination

object AccessLogsDestination : NavigationDestination {
    override val route = "Access Logs"
}

@Composable
fun AccessLogsScreen(
    onClickBottomBar: (String) -> Unit,
    currentScreen: String,
    modifier: Modifier = Modifier,
    accessViewModel: AccessLogViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val logUiState by accessViewModel.accessLogUiState.collectAsState()

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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(id = R.string.access_logs),
                    style = MaterialTheme.typography.displayLarge,
                )
                Spacer(modifier = Modifier.height(5.dp))
                LazyColumn {
                    items(logUiState.logList.size) { logs ->
                        LogsCard(
                            logs = logUiState.logList[logs],
                            modifier = Modifier.padding(
                                vertical = dimensionResource(R.dimen.padding_medium)
                            )
                        )
                    }
                }

            }

        }
    }

}

@Composable
private fun LogsCard(
    logs: AccessLogs,
    modifier: Modifier = Modifier
) {
    val name by remember { mutableStateOf(logs.name) }
    val timeIn by remember { mutableStateOf(logs.timeInEntered) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(Color(0xFF77C89D)),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.name_of_entered, name),)
                Text(text = stringResource(R.string.date_and_time, timeIn),)
            }
        }
    }
}