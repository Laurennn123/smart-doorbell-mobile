package com.example.mobileapp.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Settings(
    @StringRes val title: Int,
    val icon: ImageVector
)
