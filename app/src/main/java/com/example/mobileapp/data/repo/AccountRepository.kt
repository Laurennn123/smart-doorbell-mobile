package com.example.mobileapp.data.repo

import com.example.mobileapp.data.table.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun createAccount(account: Account)
    fun getStatus(email: String): Flow<Boolean>
    fun getName(email: String): Flow<String>
}