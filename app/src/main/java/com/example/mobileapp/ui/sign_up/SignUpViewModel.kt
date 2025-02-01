package com.example.mobileapp.ui.sign_up

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignUpViewModel: ViewModel() {


    var signUpUiState by mutableStateOf(SignUpUiState())
        private set

    fun updateUiState(signUpDetails: SignUpDetails) {
        signUpUiState = SignUpUiState(signUpDetails = signUpDetails)
    }

    fun updateDateBirthAndPicker(date: String) {
        signUpUiState = SignUpUiState(
            signUpDetails = SignUpDetails().copy(dateOfBirth = date),
            showDatePicker = false)
    }

    fun clickedCalendarIcon() {
        signUpUiState = SignUpUiState(showDatePicker = true)
    }

    fun clickedArrowDownIcon() {
        signUpUiState = SignUpUiState(showMenuPicker = true)
    }

    fun updateGender(pickedGender: String) {
        signUpUiState = SignUpUiState(
            signUpDetails = SignUpDetails().copy(
                gender = pickedGender
            ),
        )
    }

    fun dismissMenu() {
        signUpUiState = SignUpUiState(showMenuPicker = false)
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}

data class SignUpUiState(
    val signUpDetails: SignUpDetails = SignUpDetails(),
    val isEntryValid: Boolean = false,
    val showDatePicker: Boolean = false,
    val showMenuPicker: Boolean = false
)

data class SignUpDetails(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val reEnterPassword: String = "",
    val dateOfBirth: String = "",
    val gender: String = ""
)