package com.example.mobileapp.ui.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val database = Firebase.firestore
        lateinit var auth: FirebaseAuth
    }

    private var _loginUiState = MutableStateFlow(LoginUiState())

    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    var isIconPassClicked by mutableStateOf(false)
        private set

    @OptIn(UnstableApi::class)
    suspend fun isEmailPassRegistered(): Boolean {
        auth = FirebaseAuth.getInstance()
        val email = _loginUiState.value.loginDetails.email
        val password = _loginUiState.value.loginDetails.password
        var isSucess = false
        val TAG = "database"
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isSucess = true
                }
            }.await()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }

        return isSucess
    }

    @OptIn(UnstableApi::class)
    suspend fun getFullName():String {
        var fullName = ""
        database.collection("Account").get()
            .addOnSuccessListener { data ->
                for (document in data) {
                    val email = document.id
                    if (email == _loginUiState.value.loginDetails.email) {
                        fullName = document.get("Full Name").toString()
                        break
                    }
                }
            }.await()
        return fullName
    }

    @OptIn(UnstableApi::class)
    suspend fun sendPasswordResetEmail() {
        val TAG = "database"
        Firebase.auth.sendPasswordResetEmail(_loginUiState.value.loginDetails.email).
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
            }
        }.await()
    }

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