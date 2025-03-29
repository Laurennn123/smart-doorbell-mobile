package com.example.mobileapp.data.repo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.google.firebase.firestore.remote.Datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserStatusRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object{
        val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
    }

    suspend fun saveUserLoggedIn(isUserLoggedIn: Boolean) {
        dataStore.edit { status ->
            status[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }

    suspend fun getCurrentUserLoginState(): Boolean {
        return dataStore.data.first()[IS_USER_LOGGED_IN] ?: false
    }

    val isUserLoggedIn: Flow<Boolean> = dataStore.data.map { status ->
        status[IS_USER_LOGGED_IN] ?: false
    }.catch {
        if(it is IOException) {
            val TAG = "database"
            Log.e(TAG, "Error reading preference", it)
        } else {
            throw it
        }
    }
}