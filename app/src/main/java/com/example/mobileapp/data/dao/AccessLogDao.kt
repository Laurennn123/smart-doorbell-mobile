package com.example.mobileapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobileapp.data.table.AccessLogs
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessLogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLog(log: AccessLogs)

    @Query("SELECT * from Logs ORDER BY name ASC")
    fun getAllLogs(): Flow<List<AccessLogs>>
}