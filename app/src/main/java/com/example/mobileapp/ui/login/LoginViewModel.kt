package com.example.mobileapp.ui.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.mobileapp.data.repo.AccountRepository
import com.example.mobileapp.data.repo.UserStatusRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class LoginViewModel(
    private val accountRepository: AccountRepository,
    private val userStatusRepository: UserStatusRepository
) : ViewModel() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val database = Firebase.firestore
        lateinit var auth: FirebaseAuth
    }

    private var _loginUiState = MutableStateFlow(LoginUiState())

    val userState: StateFlow<UserStatusState> =
        userStatusRepository.isUserLoggedIn.map { isUserLogIn ->
            UserStatusState(isUserLogIn)
        }.distinctUntilChanged()
            .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserStatusState(runBlocking { userStatusRepository.getCurrentUserLoginState() })
        )
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    var isIconPassClicked by mutableStateOf(false)
        private set

    var isLogInClicked by mutableStateOf(false)
        private set

    fun userStatusLogIn(isUserLogIn: Boolean) {
        viewModelScope.launch {
            userStatusRepository.saveUserLoggedIn(isUserLogIn)
        }
    }

    fun clearEmailField(loginDetails: LoginUiDetails) {
        _loginUiState.value = LoginUiState(loginDetails = loginDetails)
    }

    @OptIn(UnstableApi::class)
    suspend fun isEmailPassRegistered(loginDetails: LoginUiDetails): Boolean {
        auth = FirebaseAuth.getInstance()
        val email = _loginUiState.value.loginDetails.email
        val password = _loginUiState.value.loginDetails.password
        var isSucess = false
        val TAG = "database"
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
    val isUserLoggedIn: Boolean = false
)

data class LoginUiState(
    val loginDetails: LoginUiDetails = LoginUiDetails(),
    val errorMessage: String = ""
)

data class LoginUiDetails(
    val email: String = "",
    val password: String = ""
)