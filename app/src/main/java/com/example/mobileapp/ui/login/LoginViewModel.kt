package com.example.mobileapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private var _loginUiState = MutableStateFlow(LoginUiState())

    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    var isIconPassClicked by mutableStateOf(false)
        private set

    fun updateUi(loginDetails: LoginUiDetails) {
        _loginUiState.value = LoginUiState(loginDetails = loginDetails, isEntryValid = validateInput())
    }

    fun showPassword() {
        isIconPassClicked = !isIconPassClicked
    }

    private fun validateInput():Boolean {
        return with(loginUiState.value.loginDetails) {
            email.isNotBlank() && password.isNotBlank()
        }
    }
}

data class LoginUiState(
    val loginDetails: LoginUiDetails = LoginUiDetails(),
    val isEntryValid: Boolean = false,
)

data class LoginUiDetails(
    val email: String = "",
    val password: String = ""
)