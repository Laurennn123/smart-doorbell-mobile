package com.example.mobileapp.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

data class HomeUiState(
    val isHaveHumanOnFrontDoor: Boolean = false,
    val listOfRecentVisitors: MutableList<@Composable () -> Unit> = mutableStateListOf(),
    val listOfRecentNames: MutableSet<String> = mutableSetOf()
)
