package com.example.mobileapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.datastore.core.DataStore
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.mobileapp.data.AppContainer
import com.example.mobileapp.data.AppDataContainer
import com.example.mobileapp.data.repo.UserStatusRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "USER_STATUS"
)

class SmartDoorbellApplication : Application() {
    lateinit var container: AppContainer
    lateinit var userStatusRepository: UserStatusRepository

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        userStatusRepository = UserStatusRepository(dataStore)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.name_channel)
        val descriptionText = getString(R.string.description_channel)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val frontDoorId = "front_door_channel"
        val channel = NotificationChannel(frontDoorId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}