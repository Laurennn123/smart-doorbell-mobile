package com.example.mobileapp.ui.login

data class LoginUiState(
    val loginDetails: LoginUiDetails = LoginUiDetails(),
    val errorMessage: String = "",
    val isIconPassClicked: Boolean = false,
    val isLogInClicked: Boolean = false,
)

data class LoginUiDetails(
    val email: String = "",
    val password: String = ""
)