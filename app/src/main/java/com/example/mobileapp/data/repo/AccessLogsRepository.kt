package com.example.mobileapp.data.repo

import com.example.mobileapp.data.table.AccessLogs
import kotlinx.coroutines.flow.Flow

interface AccessLogsRepository {
    suspend fun insertLog(log: AccessLogs)
    suspend fun updateLog(log: AccessLogs)
    fun isUserTappedIn(notifyName: String): Flow<String>
    fun getAllLogs(): Flow<List<AccessLogs>>
}