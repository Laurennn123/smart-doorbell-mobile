package com.example.mobileapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobileapp.SmartDoorbellApplication
import com.example.mobileapp.model.HomeScreenModel
import com.example.mobileapp.ui.account.MyAccountViewModel
import com.example.mobileapp.ui.login.LoginViewModel
import com.example.mobileapp.ui.sign_up.SignUpViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { SignUpViewModel(smartDoorbellApplication().container.accountRepository) }
        initializer { LoginViewModel(smartDoorbellApplication().userStatusRepository) }
        initializer { HomeScreenModel(smartDoorbellApplication().container.accountRepository) }
        initializer { MyAccountViewModel() }
    }
}

fun CreationExtras.smartDoorbellApplication(): SmartDoorbellApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SmartDoorbellApplication)