package com.example.mobileapp.ui.components

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit

@Composable
fun SimpleButton(
    onClick: () -> Unit,
    nameOfButton: String,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape,
    enabled: Boolean = true,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        enabled = enabled
    ) {
        Text(
            text = nameOfButton,
            fontSize = fontSize
        )
    }
}