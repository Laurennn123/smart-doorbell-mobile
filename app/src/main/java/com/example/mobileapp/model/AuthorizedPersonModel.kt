package com.example.mobileapp.model

import androidx.lifecycle.ViewModel
import com.example.mobileapp.data.AuthorizedUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthorizedPersonModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthorizedUiState())
    val uiState: StateFlow<AuthorizedUiState> = _uiState.asStateFlow()
}