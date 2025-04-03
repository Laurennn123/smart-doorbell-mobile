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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyAccountViewModel(private val accountRepository: AccountRepository): ViewModel() {


    var myAccountUiState by mutableStateOf(MyAccountUiState())

    private val _birthDateState = MutableStateFlow("")
    private val _userNameState = MutableStateFlow("")
    private val _addressState = MutableStateFlow("")
    private val _contactNumberState = MutableStateFlow("")


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

    @OptIn(ExperimentalCoroutinesApi::class)
    val userNameDb: StateFlow<String> = _userNameState
        .flatMapLatest { birthDate ->
            if (birthDate.isNotBlank()) accountRepository.getUserName(birthDate)
            else flowOf("")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _birthDateState.value
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val addressDb: StateFlow<String> = _addressState
        .flatMapLatest { birthDate ->
            if (birthDate.isNotBlank()) accountRepository.getAddress(birthDate)
            else flowOf("")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _birthDateState.value
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val contactNumberDb: StateFlow<String> = _contactNumberState
        .flatMapLatest { birthDate ->
            if (birthDate.isNotBlank()) accountRepository.getContactNumber(birthDate)
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

    fun updateAddress(address: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepository.updateAddress(address = address, email = email)
        }
    }

    fun updateUsername(address: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepository.updateUsername(userName = address, email = email)
        }
    }

    fun updateContactNumber(address: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepository.updateContactNumber(contactNumber = address, email = email)
        }
    }

    fun setEmail(email: String) {
        _birthDateState.value = email
        _userNameState.value = email
        _addressState.value = email
        _contactNumberState.value = email
    }

    var userButtonDetailClicked by mutableStateOf(false)

    var buttonClicked by mutableStateOf("")

    fun updateClicked(buttonClick: String) {
        userButtonDetailClicked = !userButtonDetailClicked
        buttonClicked = buttonClick
    }

}

data class MyAccountUiState(
    val userName: String = "",
    var address: String = "",
    val contactNumber: String = "",
    val birthDate: String = "",
)

