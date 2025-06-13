package com.example.mobileapp.ui.login

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.mobileapp.data.repo.AccountRepository
import com.example.mobileapp.data.repo.UserStatusRepository
import com.example.mobileapp.ui.UserHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val userStatusRepository: UserStatusRepository) : ViewModel(), UserHandler<LoginUiDetails> {
    companion object {
        @SuppressLint("StaticFieldLeak")
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

    suspend fun userStatusLogIn(
        isUserLogIn: Boolean,
        userEmail: String
    ) {
        userStatusRepository.saveUserLoggedIn(isUserLoggedIn = isUserLogIn, userEmail = userEmail)
    }

    @OptIn(UnstableApi::class)
    suspend fun isEmailPassRegistered(): Boolean {
        auth = FirebaseAuth.getInstance()
        val email = _loginUiState.value.loginDetails.email
        val password = _loginUiState.value.loginDetails.password
        var isSucess = false
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isSucess = true
                }
            }.await()
        } catch (e: Exception) {
            _loginUiState.update { currentState -> currentState.copy(errorMessage = e.message.toString()) }
        }
        return isSucess
    }

    override fun handleChange(details: LoginUiDetails) {
        _loginUiState.update { currentState -> currentState.copy(loginDetails = details) }
    }

    fun clearEmailField() {
        _loginUiState.update { currentState -> currentState.copy(loginDetails = _loginUiState.value.loginDetails.copy(email = "")) }
    }

    fun showPassword() {
        _loginUiState.update { currentState -> currentState.copy(isIconPassClicked = !_loginUiState.value.isIconPassClicked) }
    }

    fun logInClicked() {
        _loginUiState.update { currentState -> currentState.copy(isLogInClicked = !_loginUiState.value.isLogInClicked) }
    }

}

data class UserStatusState(
    val isUserLoggedIn: Boolean = false,
    val userEmail: String = ""
)