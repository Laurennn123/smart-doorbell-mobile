package com.example.mobileapp.data.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Logs")
data class AccessLogs(
    @PrimaryKey
    val name: String = "",
    val timeInEntered: String = "",
    val timeOut: String = "",
    val isEntered: Boolean = false
)

