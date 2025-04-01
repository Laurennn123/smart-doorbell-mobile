package com.example.mobileapp.data.repo

import com.example.mobileapp.data.table.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun createAccount(account: Account)
    fun getName(email: String): Flow<String>
    fun getBirthdate(email: String): Flow<String>
    suspend fun updateBirthDate(dateBirth: String, email: String)
}