package com.example.mobileapp.ui.sign_up

import android.annotation.SuppressLint
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.mobileapp.data.repo.AccountRepository
import com.example.mobileapp.data.table.Account
import com.example.mobileapp.ui.UserHandler
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignUpViewModel(private val accountRepository: AccountRepository) : ViewModel(), UserHandler<SignUpDetails> {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val database = Firebase.firestore
        lateinit var auth: FirebaseAuth
    }

    private var _signupUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState: StateFlow<SignUpUiState> = _signupUiState.asStateFlow()

    var errorMessage by mutableStateOf("")

    override fun handleChange(details: SignUpDetails) {
        _signupUiState.update { currentState -> currentState.copy(
            isEntryValid = validateInput(details),
            signUpDetails = details
        )}
    }

    fun openCalendar(details: SignUpDetails) {
        _signupUiState.update { currentState -> currentState.copy(
            signUpDetails = details,
            isEntryValid = validateInput(details),
            isDatePickerClicked = !_signupUiState.value.isDatePickerClicked
        )}
    }

    fun openGenderPicker(details: SignUpDetails) {
        _signupUiState.update { currentState -> currentState.copy(
            signUpDetails = details,
            isEntryValid = validateInput(details),
            isGenderPickerClicked = !_signupUiState.value.isGenderPickerClicked
        )}
    }

    fun signUpClicked() {
        _signupUiState.update { currentState -> currentState.copy(
            isSignUpClicked = !_signupUiState.value.isSignUpClicked
        )}
    }

    suspend fun updateSignUpDetails(email: String) {
        val cloudDB = Firebase.firestore
        withContext(Dispatchers.IO) {
            cloudDB.collection("Account").document(email).get()
                .addOnSuccessListener { data ->
                    _signupUiState.update { currentState ->
                        currentState.copy(
                            signUpDetails = _signupUiState.value.signUpDetails.copy(
                                fullName = data.get("Full Name").toString(),
                                email = data.get("Email").toString(),
                                password = data.get("Password").toString(),
                                dateOfBirth = data.get("Birth Date").toString(),
                                gender = data.get("Gender").toString()
                            )
                        )
                    }
                }.await()
        }
    }

    fun clearStateSignUpDetails() {
        _signupUiState.value = SignUpUiState()
    }

    suspend fun addLocalDB(email: String) {
        val fullName = accountRepository.getName(email).firstOrNull()
        if (fullName?.isBlank() != false) {
            updateSignUpDetails(email)
            accountRepository.createAccount(signUpUiState.value.signUpDetails.toAccount())
        } else {
            withContext(Dispatchers.Main) {
                clearStateSignUpDetails()
            }
        }
    }

    fun updateErrorMessage(message: String) {
        errorMessage = message
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    suspend fun addAccountInAuthentication() {
        auth = FirebaseAuth.getInstance()
        try {
            auth.createUserWithEmailAndPassword(signUpUiState.value.signUpDetails.email, signUpUiState.value.signUpDetails.password).await()
        } catch(e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    suspend fun addAccountInFireStore() {
        val account = hashMapOf(
            "Full Name" to signUpUiState.value.signUpDetails.fullName,
            "Email" to signUpUiState.value.signUpDetails.email,
            "Password" to signUpUiState.value.signUpDetails.password,
            "Birth Date" to signUpUiState.value.signUpDetails.dateOfBirth,
            "Gender" to signUpUiState.value.signUpDetails.gender,
            "Username" to signUpUiState.value.signUpDetails.userName,
            "Address" to signUpUiState.value.signUpDetails.address,
            "Contact Number" to signUpUiState.value.signUpDetails.contactNumber,
            "Profile pic" to signUpUiState.value.signUpDetails.profilePic,
        )
        try {
            database.collection("Account").document(signUpUiState.value.signUpDetails.email).set(account).await()
        } catch(e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    suspend fun addAccountForLocalDB() {
        try {
            if(validateInput()) accountRepository.createAccount(signUpUiState.value.signUpDetails.toAccount())
        } catch(e: Exception) {
            errorMessage = e.message.toString()
        }
    }

   @OptIn(ExperimentalMaterial3Api::class)
   fun selectedDate(datePickerState: DatePickerState):String {
       return datePickerState.selectedDateMillis?.let {
           convertMillisToDate(it)
       }?: ""
   }

    private fun validateInput(details: SignUpDetails = signUpUiState.value.signUpDetails):Boolean {
        return with(details) {
            fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank()
            reEnterPassword.isNotBlank() && dateOfBirth.isNotBlank() && gender.isNotBlank()
        }
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

}

fun SignUpDetails.toAccount(): Account = Account(
    fullName = fullName,
    email = email,
    password = password,
    reEnterPassword = reEnterPassword,
    dateOfBirth = dateOfBirth,
    gender = gender,
    userName = userName,
    address = address,
    contactNumber = contactNumber,
    profilePic = profilePic
)