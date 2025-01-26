package com.example.mobileapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ImageContainer(
    @DrawableRes faceImage: Int,
    imageSize: Int,
    contentDescription: String) {
    Image(
        painter = painterResource(faceImage),
        modifier = Modifier
            .size(imageSize.dp)
            .clip(MaterialTheme.shapes.extraLarge),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription
    )
}