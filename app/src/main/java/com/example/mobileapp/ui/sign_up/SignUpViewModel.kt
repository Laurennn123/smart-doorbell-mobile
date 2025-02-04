package com.example.mobileapp.ui.sign_up

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignUpViewModel() : ViewModel() {
//    private val _uiState = MutableStateFlow(SignUpUiState())
//    val signUpUiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    var signUpUiState by mutableStateOf(SignUpUiState())
        private set

    var date by mutableStateOf("")
        private set

    var gender by mutableStateOf("")
        private set

    var showDatePicker by mutableStateOf(false)
        private set

    var showMenuPicker by mutableStateOf(false)
        private set

    fun updateDate(newDate: String) {
        date = newDate
    }

   @OptIn(ExperimentalMaterial3Api::class)
   fun selectedDate(datePickerState: DatePickerState):String {
       return datePickerState.selectedDateMillis?.let {
           convertMillisToDate(it)
       }?: ""
   }

    fun updateUiState(signUpDetails: SignUpDetails) {
        signUpUiState = SignUpUiState(
            signUpDetails = signUpDetails,
            isEntryValid = validateInput(signUpDetails))
    }

    fun onDismissCalendar() {
        showDatePicker = false
        updateField(signUpUiState.signUpDetails)
        if (signUpUiState.signUpDetails.dateOfBirth.isNotBlank()) {
            signUpUiState = SignUpUiState(
                signUpDetails = signUpUiState.signUpDetails,
                isEntryValid = validateInput(signUpUiState.signUpDetails))
        }
    }

    fun clickedCalendarIcon() {
        showDatePicker = true
    }

    fun clickedArrowDownIcon() {
        showMenuPicker = true
    }

    fun onDismissGenderPicker() {
        showMenuPicker = false
    }

    fun updateGender(pickedGender: String) {
        gender = pickedGender
        showMenuPicker = false
        updateField(signUpUiState.signUpDetails)
        if (date.isNotBlank()) {
            signUpUiState = SignUpUiState(
                signUpDetails = signUpUiState.signUpDetails,
                isEntryValid = validateInput(signUpUiState.signUpDetails))
        }
    }

    private fun updateField(signUpDetails: SignUpDetails) {
        signUpUiState = SignUpUiState(
            signUpDetails = signUpDetails.copy(
                fullName = signUpDetails.fullName,
                email = signUpDetails.email,
                password = signUpDetails.password,
                reEnterPassword = signUpDetails.reEnterPassword,
                gender = gender,
                dateOfBirth = date
            )
        )
    }

//    suspend fun addAccount() {
//        if (validateInput()) {
//            signUpDao.insertAccount(signUpUiState.signUpDetails.toSignUp())
//        }
//    }

    private fun validateInput(uiState: SignUpDetails = signUpUiState.signUpDetails):Boolean {
        return with(uiState) {
            fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank()
            reEnterPassword.isNotBlank() && dateOfBirth.isNotBlank() && gender.isNotBlank()
        }
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

//    companion object {
//        val factory : ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[APPLICATION_KEY] as SmartDoorbellApplication)
//                SignUpViewModel(application.database.signUpDao())
//            }
//        }
//    }
}

data class SignUpUiState (
    val signUpDetails: SignUpDetails = SignUpDetails(),
    val isEntryValid: Boolean = false,
)

data class SignUpDetails(
    val id: Int = 0,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val reEnterPassword: String = "",
    val dateOfBirth: String = "",
    val gender: String = ""
)

//fun SignUpDetails.toSignUp(): SignUp = SignUp(
//    id = id,
//    fullName = fullName,
//    email = email,
//    password = password,
//    reEnterPassword = reEnterPassword,
//    dateOfBirth = dateOfBirth,
//    gender = gender
//)