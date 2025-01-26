package com.example.mobileapp.model

import androidx.annotation.DrawableRes

data class AuthorizedPerson(
    @DrawableRes val faceImage: Int,
    val name: String,
    val relationship: String
)
