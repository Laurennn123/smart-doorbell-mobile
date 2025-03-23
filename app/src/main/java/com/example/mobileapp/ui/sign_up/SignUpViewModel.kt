package com.example.mobileapp.ui.sign_up

import android.annotation.SuppressLint
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.mobileapp.data.repo.AccountRepository
import com.example.mobileapp.data.table.Account
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignUpViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val database = Firebase.firestore
        lateinit var auth: FirebaseAuth
    }

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

    @androidx.annotation.OptIn(UnstableApi::class)
    fun addAccountCloud() {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(signUpUiState.signUpDetails.email, signUpUiState.signUpDetails.password)
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    fun addAccountCloudInformation() {
        val account = hashMapOf(
            "Full Name" to signUpUiState.signUpDetails.fullName,
            "Email" to signUpUiState.signUpDetails.email,
            "Password" to signUpUiState.signUpDetails.password,
            "Birth Date" to date,
            "Gender" to gender
        )
        val TAG = "database"
        database.collection("Account").document(signUpUiState.signUpDetails.email).set(account)
            .addOnSuccessListener { Log.d(TAG, "sucess") }.addOnFailureListener { e-> Log.d(TAG, "failed", e) }
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

fun SignUpDetails.toAccount(): Account = Account(
    id = id,
    fullName = fullName,
    email = email,
    password = password,
    reEnterPassword = reEnterPassword,
    dateOfBirth = dateOfBirth,
    gender = gender
)