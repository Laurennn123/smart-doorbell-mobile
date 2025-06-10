package com.example.mobileapp.ui.sign_up

data class SignUpUiState (
    val signUpDetails: SignUpDetails = SignUpDetails(),
    val isEntryValid: Boolean = false,
    val isDatePickerClicked: Boolean = false,
    val isGenderPickerClicked: Boolean = false,
    val isSignUpClicked: Boolean = false
)

data class SignUpDetails(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val reEnterPassword: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val userName: String = "Username",
    val address: String = "Address",
    val contactNumber: String = "Contact Number",
    val profilePic: String = ""
)