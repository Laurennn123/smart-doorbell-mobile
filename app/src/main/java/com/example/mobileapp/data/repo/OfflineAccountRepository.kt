package com.example.mobileapp.data.repo

import com.example.mobileapp.data.dao.SignUpDao
import com.example.mobileapp.data.table.Account
import kotlinx.coroutines.flow.Flow

class OfflineAccountRepository(private val signUpDao: SignUpDao): AccountRepository {
    override suspend fun createAccount(account: Account) = signUpDao.insertAccount(account)
//    override fun getStatus(email: String): Flow<Boolean> = signUpDao.getStatus(email)
    override fun getName(email: String): Flow<String> = signUpDao.getName(email)
    override fun getUserName(email: String): Flow<String> = signUpDao.getUserName(email)
    override fun getAddress(email: String): Flow<String> = signUpDao.getAddress(email)
    override fun getContactNumber(email: String): Flow<String> = signUpDao.getContactNumber(email)
    override fun getBirthdate(email: String): Flow<String> = signUpDao.getBirthDate(email)
    override fun getPropertiesOfUser(email: String): Flow<Account> = signUpDao.getPropertiesOfUser(email)
    override suspend fun updateAddress(address: String, email: String) = signUpDao.updateAddress(address, email)
    override suspend fun updateBirthDate(dateBirth: String, email: String) = signUpDao.updateBirthDate(dateBirth, email)
    override suspend fun updateUsername(userName: String, email: String) = signUpDao.updateUsername(userName, email)
    override suspend fun updateContactNumber(contactNumber: String, email: String) = signUpDao.updateContactNumber(contactNumber, email)
}