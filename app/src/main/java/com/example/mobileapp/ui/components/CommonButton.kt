package com.example.mobileapp.ui.components

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleButton(
    onClick: () -> Unit,
    nameOfButton: String,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
    ) {
        Text( text = nameOfButton )
    }
}