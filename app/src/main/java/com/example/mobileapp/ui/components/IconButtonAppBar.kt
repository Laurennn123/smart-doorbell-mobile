package com.example.mobileapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconAppBar(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String,
    color: Color = Color.White,
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = color,
            modifier = modifierIcon
        )
    }
}

@Composable
fun DefaultIcon(
    icon: ImageVector,
    nameOfIcon: String,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icon,
        contentDescription = nameOfIcon,
        modifier = modifier
    )
}