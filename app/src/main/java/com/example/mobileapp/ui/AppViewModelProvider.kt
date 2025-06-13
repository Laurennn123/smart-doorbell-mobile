package com.example.mobileapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobileapp.SmartDoorbellApplication
import com.example.mobileapp.ui.access_logs_screen.AccessLogViewModel
import com.example.mobileapp.ui.account.MyAccountViewModel
import com.example.mobileapp.ui.forget_password.ForgetPasswordViewModel
import com.example.mobileapp.ui.home.HomeScreenViewModel
import com.example.mobileapp.ui.login.LoginViewModel
import com.example.mobileapp.ui.sign_up.SignUpViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { SignUpViewModel(smartDoorbellApplication().container.accountRepository) }
        initializer { LoginViewModel(smartDoorbellApplication().userStatusRepository) }
        initializer { HomeScreenViewModel(smartDoorbellApplication().container.accountRepository) }
        initializer { MyAccountViewModel(smartDoorbellApplication().container.accountRepository) }
        initializer { AccessLogViewModel(smartDoorbellApplication().container.logsRepository) }
        initializer { ForgetPasswordViewModel() }
    }
}

fun CreationExtras.smartDoorbellApplication(): SmartDoorbellApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SmartDoorbellApplication)