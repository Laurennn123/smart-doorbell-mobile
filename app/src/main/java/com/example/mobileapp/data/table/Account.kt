package com.example.mobileapp.data.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Account")
data class Account(
    @PrimaryKey
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val reEnterPassword: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val userName: String = "Username",
    val address: String = "Address",
    val contactNumber: String = "Contact Number",
    val profilePic: String = ""
)
