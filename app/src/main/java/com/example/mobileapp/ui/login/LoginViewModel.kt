package com.example.mobileapp.ui.login

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.mobileapp.data.repo.UserStatusRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val userStatusRepository: UserStatusRepository) : ViewModel() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val database = Firebase.firestore
        lateinit var auth: FirebaseAuth
    }

    private var _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    val userSession: StateFlow<UserStatusState> = userStatusRepository.userSession
        .map { session ->
            UserStatusState(isUserLoggedIn = session.isUserLoggedIn, userEmail = session.userEmail)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserStatusState(
                isUserLoggedIn = runBlocking { userStatusRepository.getCurrentUserLoginState() },
                userEmail = runBlocking { userStatusRepository.getCurrentEmail() }
            )
        )

    var isIconPassClicked by mutableStateOf(false)
        private set

    var isLogInClicked by mutableStateOf(false)
        private set

    suspend fun userStatusLogIn(
        isUserLogIn: Boolean,
        userEmail: String
    ) {
        userStatusRepository.saveUserLoggedIn(isUserLoggedIn = isUserLogIn, userEmail = userEmail)
    }

    fun clearEmailField(loginDetails: LoginUiDetails) {
        _loginUiState.value = LoginUiState(loginDetails = loginDetails)
    }

    @OptIn(UnstableApi::class)
    suspend fun isEmailPassRegistered(loginDetails: LoginUiDetails): Boolean {
        val TAG = "authentication"
        auth = FirebaseAuth.getInstance()
        val email = _loginUiState.value.loginDetails.email
        val password = _loginUiState.value.loginDetails.password
        var isSucess = false
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isSucess = true
                    Log.d(TAG, "success!")
                }
            }.await()
        } catch (e: Exception) {
            _loginUiState.value = LoginUiState(loginDetails = loginDetails, errorMessage = e.message.toString())
            Log.d(TAG, e.message.toString())
        }
        return isSucess
    }

    @OptIn(UnstableApi::class)
    fun sendPasswordResetEmail(email: String): String {
        val tag = "forgot"
        var message = ""
        try {
            Firebase.auth.sendPasswordResetEmail(email)
        } catch(e: Exception) {
            Log.d(tag, e.message.toString())
            message = e.message.toString()
        }
        return message
    }

    fun updateUi(loginDetails: LoginUiDetails) {
        _loginUiState.value = LoginUiState(loginDetails = loginDetails)
    }

    fun showPassword() {
        isIconPassClicked = !isIconPassClicked
    }

    fun logInClicked() {
        isLogInClicked = !isLogInClicked
    }
}

data class UserStatusState(
    val isUserLoggedIn: Boolean = false,
    val userEmail: String = ""
)

data class LoginUiState(
    val loginDetails: LoginUiDetails = LoginUiDetails(),
    val errorMessage: String = ""
)

data class LoginUiDetails(
    val email: String = "",
    val password: String = ""
)