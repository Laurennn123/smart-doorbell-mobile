package com.example.mobileapp.model

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.mobileapp.data.EntriesHistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date

class EntriesHistoryModel: ViewModel() {
    private val _uiState = MutableStateFlow(EntriesHistoryUiState())
    val uiState: StateFlow<EntriesHistoryUiState> = _uiState.asStateFlow()

    @SuppressLint("SimpleDateFormat")
    private var formaterDate = SimpleDateFormat("EEE, MMM. d, yyyy")
    @SuppressLint("SimpleDateFormat")
    private var formaterTime = SimpleDateFormat("h:mm a")
    var currentDate: String = formaterDate.format(Date())
    var currentTime: String = formaterTime.format(Date())

    @SuppressLint("SimpleDateFormat")
    fun updateTimeAndDate() {
        formaterDate = SimpleDateFormat("EEE, MMM. d, yyyy")
        formaterTime = SimpleDateFormat("h:mm a")
        currentDate = formaterDate.format(Date())
        currentTime = formaterTime.format(Date())
    }
}