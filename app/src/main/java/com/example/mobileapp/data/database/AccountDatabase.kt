package com.example.mobileapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.example.mobileapp.data.dao.SignUpDao
import com.example.mobileapp.data.table.Account
import android.content.Context


@Database(entities = [Account::class], version = 1, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun signUpDao(): SignUpDao

    companion object {
        @Volatile
        private var Instance: AccountDatabase? = null

        fun getDatabase(context: Context): AccountDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AccountDatabase::class.java, "account_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}