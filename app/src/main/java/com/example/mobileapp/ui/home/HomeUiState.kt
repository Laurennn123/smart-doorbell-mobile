package com.example.mobileapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

data class HomeUiState(
    val fullNameOfCurrentUser: String = "",
    val nameOfRegisteringForCard: String = "",
    val isUnlockDoorClicked: Boolean = false,
    val isHaveHumanOnFrontDoor: Boolean = false,
    val listOfRecentVisitors: MutableList<@Composable () -> Unit> = mutableStateListOf(),
    val listOfRecentNames: MutableSet<String> = mutableSetOf()
)