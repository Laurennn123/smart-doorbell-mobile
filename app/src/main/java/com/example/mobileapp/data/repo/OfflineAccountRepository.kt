package com.example.mobileapp.data.repo

import com.example.mobileapp.data.dao.SignUpDao
import com.example.mobileapp.data.table.Account

class OfflineAccountRepository(private val signUp: SignUpDao) :AccountRepository {
    override suspend fun createAccount(account: Account) = signUp.insertAccount(account)
}