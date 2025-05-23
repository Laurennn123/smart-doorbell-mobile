package com.example.mobileapp.data.repo

import com.example.mobileapp.data.dao.AccessLogDao
import com.example.mobileapp.data.table.AccessLogs
import kotlinx.coroutines.flow.Flow

class OfflineLogsRepository(private val logsDao: AccessLogDao): AccessLogsRepository {
    override suspend fun insertLog(log: AccessLogs) = logsDao.insertLog(log)
    override suspend fun updateLog(log: AccessLogs) = logsDao.updateLog(log)
    override fun isUserTappedIn(notifyName: String): Flow<String> = logsDao.isUserTappedIn(notifyName)
    override fun getAllLogs(): Flow<List<AccessLogs>> = logsDao.getAllLogs()
}