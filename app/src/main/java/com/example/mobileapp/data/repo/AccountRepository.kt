package com.example.mobileapp.data.repo

import com.example.mobileapp.data.table.Account

interface AccountRepository {
    suspend fun createAccount(account: Account)
}