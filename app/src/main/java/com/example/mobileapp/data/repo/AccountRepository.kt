package com.example.mobileapp.data.repo

import com.example.mobileapp.data.table.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun createAccount(account: Account)
    fun getName(email: String): Flow<String>
    fun getBirthdate(email: String): Flow<String>
    fun getUserName(email: String): Flow<String>
    fun getAddress(email: String): Flow<String>
    fun getContactNumber(email: String): Flow<String>
    fun getProfilePic(email: String): Flow<String>
    fun getPassword(email: String): Flow<String>
    suspend fun updatePassword(newPassword: String, email: String)
    suspend fun updateProfilePic(uri: String, email: String)
    suspend fun updateAddress(address: String, email: String)
    suspend fun updateContactNumber(contactNumber: String, email: String)
    suspend fun updateUsername(userName: String, email: String)
    suspend fun updateBirthDate(dateBirth: String, email: String)
}