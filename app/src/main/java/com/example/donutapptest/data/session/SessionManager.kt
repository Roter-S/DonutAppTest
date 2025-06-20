package com.example.donutapptest.data.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "session_prefs")

class SessionManager(private val context: Context) {
    companion object {
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val KEY_USERNAME = stringPreferencesKey("username")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_IS_LOGGED_IN] ?: false
    }

    val username: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_USERNAME]
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_IS_LOGGED_IN] = loggedIn
        }
    }

    suspend fun setUsername(username: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USERNAME] = username
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs[KEY_IS_LOGGED_IN] = false
            prefs.remove(KEY_USERNAME)
        }
    }
}
