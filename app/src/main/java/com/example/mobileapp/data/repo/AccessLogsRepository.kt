package com.example.mobileapp.data.repo

import com.example.mobileapp.data.table.AccessLogs
import kotlinx.coroutines.flow.Flow

interface AccessLogsRepository {
    fun getAllLogs(): Flow<List<AccessLogs>>
}