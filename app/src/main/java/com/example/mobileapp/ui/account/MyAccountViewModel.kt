package com.example.mobileapp.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyAccountViewModel: ViewModel() {
    private val _myAccountUiState = MutableStateFlow(MyAccountUiState())
    val accountUiState: StateFlow<MyAccountUiState> = _myAccountUiState.asStateFlow()

    var userName by mutableStateOf("")
    var address by mutableStateOf("")
    var contactNumber by mutableStateOf("")

    var userButtonDetailClicked by mutableStateOf(false)

    var buttonClicked by mutableStateOf("")

    fun updateClicked(buttonClick: String) {
        userButtonDetailClicked = true
        buttonClicked = buttonClick
    }

    fun updateUsername() {
        _myAccountUiState.value = MyAccountUiState(
            userName = userName,
            address = _myAccountUiState.value.address,
            contactNumber = _myAccountUiState.value.contactNumber,
            birthDate = _myAccountUiState.value.birthDate
        )
        onDismiss()
    }

    fun updateAddress() {
        _myAccountUiState.value = MyAccountUiState(
            address = address,
            userName = _myAccountUiState.value.userName,
            contactNumber = _myAccountUiState.value.contactNumber,
            birthDate = _myAccountUiState.value.birthDate
        )
        onDismiss()
    }

    fun updateContactNumber() {
        _myAccountUiState.value = MyAccountUiState(
            contactNumber = contactNumber,
            userName = _myAccountUiState.value.userName,
            address = _myAccountUiState.value.address,
            birthDate = _myAccountUiState.value.birthDate
        )
        onDismiss()
    }

    fun updateBirthDate(birthDate: String) {
        _myAccountUiState.value = MyAccountUiState(
            contactNumber = _myAccountUiState.value.contactNumber,
            userName = _myAccountUiState.value.userName,
            address = _myAccountUiState.value.address,
            birthDate = birthDate
        )
        onDismiss()
    }



    fun onDismiss() {
        buttonClicked = ""
        userName = ""
        address = ""
        contactNumber = ""
        userButtonDetailClicked = false
    }


}

data class MyAccountUiState(
    val userName: String = "Username",
    val address: String = "Address",
    val contactNumber: String = "Contact number",
    val birthDate: String = "Date",
    val email: String = "akotosiboss@gmail.com"
)

