package com.example.mobileapp.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.repo.AccountRepository
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

class MyAccountViewModel(private val accountRepository: AccountRepository): ViewModel() {
    private val _myAccountUiState = MutableStateFlow(MyAccountUiState())
    val accountUiState: StateFlow<MyAccountUiState> = _myAccountUiState.asStateFlow()

    private val _birthDateState = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    val birthDateDb: StateFlow<String> = _birthDateState
        .flatMapLatest { birthDate ->
            if (birthDate.isNotBlank()) accountRepository.getBirthdate(birthDate)
            else flowOf("")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _birthDateState.value
        )

    fun updateBirthDate(dateBirth: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepository.updateBirthDate(dateBirth = dateBirth, email = email)
        }
    }

    fun setBirthDate(email: String) {
        _birthDateState.value = email
    }

    var userButtonDetailClicked by mutableStateOf(false)

    var buttonClicked by mutableStateOf("")

    fun updateClicked(buttonClick: String) {
        userButtonDetailClicked = !userButtonDetailClicked
        buttonClicked = buttonClick
    }

}

data class MyAccountUiState(
    val userName: String = "Username",
    val address: String = "Address",
    val contactNumber: String = "Contact number",
    val birthDate: String = "",
    val email: String = ""
)

