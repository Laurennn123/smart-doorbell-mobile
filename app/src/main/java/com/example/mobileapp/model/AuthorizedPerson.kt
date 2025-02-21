package com.example.mobileapp.model

import android.graphics.Bitmap

data class AuthorizedPerson(
     val faceImage: Bitmap?,
    val name: String,
    val relationship: String
)
