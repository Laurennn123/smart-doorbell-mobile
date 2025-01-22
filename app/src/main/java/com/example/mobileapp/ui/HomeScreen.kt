package com.example.mobileapp.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.theme.MobileAppTheme

@Composable
fun HomeScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
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
            onClickAlarm = {},
            onClickDoor = {}
        )
        RecentVisitors(modifier = Modifier.padding(vertical = 8.dp))
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
    MessagePanel(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
    )
}

@Composable
private fun MessagePanel(modifier: Modifier = Modifier) {
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
            value = "",
            onValueChange = { },
            label = { Text(stringResource(id = R.string.enter_message)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_extra_medium)),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        Row(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            SimpleButton(
                onClick = { },
                nameOfButton = stringResource(id = R.string.clear),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
            SimpleButton(
                onClick = { },
                nameOfButton = stringResource(id = R.string.enter),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun RecentVisitors(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.recent_visitor),
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier
    )
    VisitorPlates(
        nameOfVisitors = "Borils",
        isAuthorized = true,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Composable
private fun VisitorPlates(
    nameOfVisitors: String,
    isAuthorized: Boolean,
    modifier:Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.thomas_si_boss),
            modifier = Modifier
                .size(64.dp)
                .clip(MaterialTheme.shapes.extraLarge),
            contentScale = ContentScale.Crop,
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