package com.example.mobileapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.mobileapp.data.table.Account
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SignUpDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Account)

//    @Query("SELECT isAccountLoggedIn FROM Account WHERE email = :email")
//    fun getStatus(email: String): Flow<Boolean>

    @Query("SELECT fullName FROM Account WHERE email = :email")
    fun getName(email: String): Flow<String>

    @Query("SELECT dateOfBirth FROM Account WHERE email = :email")
    fun getBirthDate(email: String): Flow<String>

    @Query("SELECT userName FROM Account WHERE email = :email")
    fun getUserName(email: String): Flow<String>

    @Query("SELECT address FROM Account WHERE email = :email")
    fun getAddress(email: String): Flow<String>

    @Query("SELECT contactNumber FROM Account WHERE email = :email")
    fun getContactNumber(email: String): Flow<String>

    @Query("SELECT * from Account WHERE email = :email")
    fun getPropertiesOfUser(email: String): Flow<Account>

    @Query("UPDATE Account SET dateOfBirth = :dateBirth WHERE email = :email")
    suspend fun updateBirthDate(dateBirth: String, email: String)

    @Query("UPDATE Account SET address = :address WHERE email = :email")
    suspend fun updateAddress(address: String, email: String)

    @Query("UPDATE Account SET contactNumber = :contactNumber WHERE email = :email")
    suspend fun updateContactNumber(contactNumber: String, email: String)

    @Query("UPDATE Account SET userName = :userName WHERE email = :email")
    suspend fun updateUsername(userName: String, email: String)
}