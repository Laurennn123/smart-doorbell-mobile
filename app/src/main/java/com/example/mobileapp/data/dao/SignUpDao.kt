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

    @Query("SELECT isAccountLoggedIn FROM Account WHERE email = :email")
    fun getStatus(email: String): Flow<Boolean>

    @Query("SELECT fullName FROM Account WHERE email = :email")
    fun getName(email: String): Flow<String>
}