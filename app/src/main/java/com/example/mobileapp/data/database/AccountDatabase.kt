package com.example.mobileapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.example.mobileapp.data.dao.SignUpDao
import com.example.mobileapp.data.table.Account
import android.content.Context
import com.example.mobileapp.data.dao.AccessLogDao
import com.example.mobileapp.data.table.AccessLogs


@Database(entities = [Account::class, AccessLogs::class], version = 1, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun signUpDao(): SignUpDao
    abstract fun accessLogDao(): AccessLogDao

    companion object {
        @Volatile
        private var Instance: AccountDatabase? = null

        fun getDatabase(context: Context): AccountDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AccountDatabase::class.java, "account_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}