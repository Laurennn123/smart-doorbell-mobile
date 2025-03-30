package com.example.mobileapp.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mobileapp.ui.login.UserStatusState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserStatusRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object{
        val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
        val USER_EMAIL = stringPreferencesKey("user_email")
    }

    suspend fun saveUserLoggedIn(
        isUserLoggedIn: Boolean,
        userEmail: String,
    ) {
        dataStore.edit { data ->
            data[IS_USER_LOGGED_IN] = isUserLoggedIn
            data[USER_EMAIL] = userEmail
        }
    }

    suspend fun getCurrentUserLoginState(): Boolean {
        return dataStore.data.first()[IS_USER_LOGGED_IN] ?: false
    }

    suspend fun getCurrentEmail(): String {
        return dataStore.data.first()[USER_EMAIL] ?: ""
    }

    val userSession: Flow<UserStatusState> = combine(
        dataStore.data.map { it[USER_EMAIL] ?: "" },
        dataStore.data.map { it[IS_USER_LOGGED_IN] ?: false }
    ) { email, isLoggedIn ->
        UserStatusState(isUserLoggedIn = isLoggedIn, userEmail = email)
    }

}