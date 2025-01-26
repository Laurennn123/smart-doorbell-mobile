package com.example.mobileapp.data

import androidx.compose.runtime.mutableStateListOf
import com.example.mobileapp.model.AuthorizedPerson

class AuthorizedUiState {
    val listOfAuthorizedPerson: MutableList<AuthorizedPerson> = mutableStateListOf()
}