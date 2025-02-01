package com.example.mobileapp.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldWithIcon(
    icon: @Composable () -> Unit = {},
    label: String,
    displayTyped: String,
    userTyped: (String) -> Unit,
    colors: TextFieldColors,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier) {
    TextField(
        value = displayTyped,
        onValueChange = userTyped,
        label = { Text(text = label) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        leadingIcon = icon,
        modifier = modifier,
        colors = colors,
        singleLine = true
    )
}