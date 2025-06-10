package com.example.mobileapp.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mobileapp.BackAndUserAppBar
import com.example.mobileapp.R
import com.example.mobileapp.model.Settings
import com.example.mobileapp.ui.components.BottomNavigationBar
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.navigation.NavigationDestination

object SettingScreenDestination : NavigationDestination {
    override val route = "Setting"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    settings: List<Settings>,
    onClick: (String) -> Unit,
    navigateUp: () -> Unit,
    currentDestination: String,
    onClickBottomBar: (String) -> Unit,
    currentScreen: String,
    modifier: Modifier = Modifier) {

    Scaffold(
        topBar = { BackAndUserAppBar(
            navigateUp = navigateUp,
            currentDestination = currentDestination,
            onClickAccount = {}
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.displayLarge
                )
                settings.forEach { option ->
                    val title = stringResource(option.title)
                    SettingsSelectButton(
                        settings = option,
                        onClick = { onClick(title) },
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSelectButton(
    settings: Settings,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(
                imageVector = settings.icon,
                contentDescription = stringResource(settings.title),
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = stringResource(settings.title),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = dimensionResource(R.dimen.padding_small)),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun FaceEnrollmentAddOrView(
    onDismissRequest: () -> Unit,
    onClickAddNew: () -> Unit,
    onClickView: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            FaceEnrollmentSelection(
                onClickAddNew = onClickAddNew,
                onClickView = onClickView,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun FaceEnrollmentSelection(
    onClickAddNew: () -> Unit,
    onClickView: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        SimpleButton(
            onClick = onClickAddNew,
            nameOfButton = stringResource(R.string.add_new_authorized_person),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        SimpleButton(
            onClick = onClickView,
            nameOfButton = stringResource(R.string.view_authorized_list),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
        )
    }
}