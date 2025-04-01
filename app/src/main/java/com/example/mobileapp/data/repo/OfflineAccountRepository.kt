package com.example.mobileapp.data.repo

import com.example.mobileapp.data.dao.SignUpDao
import com.example.mobileapp.data.table.Account
import kotlinx.coroutines.flow.Flow

class OfflineAccountRepository(private val signUpDao: SignUpDao): AccountRepository {
    override suspend fun createAccount(account: Account) = signUpDao.insertAccount(account)
//    override fun getStatus(email: String): Flow<Boolean> = signUpDao.getStatus(email)
    override fun getName(email: String): Flow<String> = signUpDao.getName(email)
    override fun getBirthdate(email: String): Flow<String> = signUpDao.getBirthDate(email)
    override suspend fun updateBirthDate(dateBirth: String, email: String) = signUpDao.updateBirthDate(dateBirth, email)
}