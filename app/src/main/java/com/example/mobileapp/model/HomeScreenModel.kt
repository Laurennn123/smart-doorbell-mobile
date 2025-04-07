package com.example.mobileapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.HomeUiState
import com.example.mobileapp.data.repo.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.Socket

class HomeScreenModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _fullNameCurrentUser = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val fullName: StateFlow<String> = _fullNameCurrentUser
        .flatMapLatest { email ->
            if (email.isNotBlank()) accountRepository.getName(email)
            else flowOf("")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _fullNameCurrentUser.value
        )

    fun setFullName(email: String) {
        _fullNameCurrentUser.value = email
    }

    var userMessage by mutableStateOf("")
        private set

    var isUnlockDoorClicked by mutableStateOf(false)
        private set

    fun doorClick() {
        isUnlockDoorClicked = !isUnlockDoorClicked
    }

    var index by mutableStateOf(0)

    fun updateMessage(userTyped: String) {
        userMessage = userTyped
    }

    fun clearMessage() {
        userMessage = ""
    }

    fun sendMessageToESP32(message: String ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket("192.168.49.132", 81)
                val writer = socket.getOutputStream().bufferedWriter()
                writer.write(message + "\n")
                writer.flush()
                writer.close()
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}