package com.example.mobileapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mobileapp.data.table.AccessLogs
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessLogDao {
    @Query("SELECT * from Logs ORDER BY name ASC")
    fun getAllLogs(): Flow<List<AccessLogs>>
}