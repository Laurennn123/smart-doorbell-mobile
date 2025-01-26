package com.example.mobileapp.data

import androidx.compose.runtime.mutableStateListOf
import com.example.mobileapp.model.EntriesHistory

data class EntriesHistoryUiState(
    val listOfEntries: MutableList<EntriesHistory> = mutableStateListOf(),
)
