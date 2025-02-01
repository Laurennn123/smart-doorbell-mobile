package com.example.mobileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.R
import com.example.mobileapp.model.HomeScreenModel
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.components.ImageContainer
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.theme.MobileAppTheme

@Composable
fun HomeScreen(
    onClick: () -> Unit,
    tryClick: () -> Unit,
    homeViewModel: HomeScreenModel = viewModel(),
    modifier: Modifier = Modifier) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        IconAppBar(
            icon = Icons.Filled.Check,
            onClick = tryClick,
            contentDescription = "Try"
        )
        RecentNotification(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .size(60.dp)
        )
        Camera(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
            .height(120.dp))
        Actions(
            onClickAlarm = {
                // example that it should automated
                val index = homeViewModel.index
                homeUiState.listOfRecentVisitors.add(
                    { VisitorCard("borils${index}", true,modifier = Modifier.padding(start = 10.dp)) }
                )
                homeViewModel.index++
            },
            onClickDoor = {}
        )
        MessagePanel(
            messageValue = homeViewModel.userMessage,
            userMessage = { homeViewModel.updateMessage(it) },
            onClickClear = { homeViewModel.clearMessage() },
            onClickEnter = { },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
        )
        RecentVisitors(
            listOfUsers = homeUiState.listOfRecentVisitors,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
private fun RecentNotification(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.recent_notification),
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
    )
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = stringResource(id = R.string.recent_notification)
            )
            Text(
                text = stringResource(id = R.string.someone_on_frontdoor),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun Camera(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.camera),
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        SimpleButton(
            onClick = { },
            nameOfButton = stringResource(id = R.string.live_view),
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
        )
    }
}

@Composable
private fun Actions(
    onClickDoor: () -> Unit,
    onClickAlarm: () -> Unit) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(id = R.string.actions),
        style = MaterialTheme.typography.displayMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
    SimpleButton(
        onClick = onClickDoor,
        nameOfButton = stringResource(id = R.string.unlock_door),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge
    )
    Spacer(modifier = Modifier.height(8.dp))
    SimpleButton(
        onClick = onClickAlarm,
        nameOfButton = stringResource(id = R.string.alarm_trigger),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun MessagePanel(
    messageValue: String,
    userMessage: (String) -> Unit,
    onClickClear: () -> Unit,
    onClickEnter: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.message_panel),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )
        TextField(
            value = messageValue,
            onValueChange = userMessage,
            label = { Text(stringResource(id = R.string.enter_message)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_extra_medium)),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
        )
        Row(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            SimpleButton(
                onClick = onClickClear,
                nameOfButton = stringResource(id = R.string.clear),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
            SimpleButton(
                onClick = onClickEnter,
                nameOfButton = stringResource(id = R.string.enter),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun RecentVisitors(
    listOfUsers: MutableList<@Composable () -> Unit>,
    modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.recent_visitor),
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier
    )
    LazyRow {
        items(listOfUsers.size) { users ->
            listOfUsers[users]()
        }
    }
}

@Composable
private fun VisitorCard(
    nameOfVisitors: String,
    isAuthorized: Boolean,
    modifier:Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ImageContainer(
            faceImage = R.drawable.thomas_si_boss,
            imageSize = 64,
            contentDescription = "bossing"
        )
        Text(text = nameOfVisitors)
        if (isAuthorized) {
            Text(text = stringResource(R.string.authorized))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun Test2() {
    MobileAppTheme {
//        HomeScreen(
//            modifier = Modifier.fillMaxWidth()
//        )
    }
}