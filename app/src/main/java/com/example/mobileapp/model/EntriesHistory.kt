package com.example.mobileapp.model

import androidx.annotation.DrawableRes

data class EntriesHistory(
    @DrawableRes val faceImage: Int,
    val name: String,
    val date: String,
    val time: String,
)
