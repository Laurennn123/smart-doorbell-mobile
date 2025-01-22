package com.example.mobileapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobileapp.R
import com.example.mobileapp.model.Settings

@Composable
fun SettingsScreen(
    settings: List<Settings>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier) {
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