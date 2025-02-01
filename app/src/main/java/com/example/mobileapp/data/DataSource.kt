package com.example.mobileapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import com.example.mobileapp.R
import com.example.mobileapp.model.Settings

object DataSource {
    val settings = listOf(
        Settings(R.string.my_account, Icons.Filled.AccountCircle),
        Settings(R.string.face_enrollment, Icons.Filled.Face),
        Settings(R.string.entries_history, Icons.Filled.Lock),
        Settings(R.string.about_us, Icons.Filled.Info),
        Settings(R.string.logout, Icons.Filled.Close),
    )
}