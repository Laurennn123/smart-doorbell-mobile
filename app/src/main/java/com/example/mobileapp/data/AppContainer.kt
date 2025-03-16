package com.example.mobileapp.data

import android.content.Context
import com.example.mobileapp.data.database.AccountDatabase
import com.example.mobileapp.data.repo.AccountRepository
import com.example.mobileapp.data.repo.OfflineAccountRepository

interface AppContainer {
    val accountRepository: AccountRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val accountRepository: AccountRepository by lazy {
        OfflineAccountRepository(AccountDatabase.getDatabase(context).signUpDao())
    }
}