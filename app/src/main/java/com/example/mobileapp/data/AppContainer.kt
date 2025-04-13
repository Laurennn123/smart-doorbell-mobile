package com.example.mobileapp.data

import android.content.Context
import com.example.mobileapp.data.database.AccountDatabase
import com.example.mobileapp.data.repo.AccessLogsRepository
import com.example.mobileapp.data.repo.AccountRepository
import com.example.mobileapp.data.repo.OfflineAccountRepository
import com.example.mobileapp.data.repo.OfflineLogsRepository

interface AppContainer {
    val accountRepository: AccountRepository
    val logsRepository: AccessLogsRepository
}

class AppDataContainer(context: Context) : AppContainer {
    val database = AccountDatabase.getDatabase(context)
    override val accountRepository: AccountRepository by lazy {
        OfflineAccountRepository(database.signUpDao())
    }
    override val logsRepository: AccessLogsRepository by lazy {
        OfflineLogsRepository(database.accessLogDao())
    }
}