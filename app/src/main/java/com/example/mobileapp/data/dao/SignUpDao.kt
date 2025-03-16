package com.example.mobileapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.mobileapp.data.table.Account
import androidx.room.OnConflictStrategy

@Dao
interface SignUpDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Account)
}