package com.example.donutapptest.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERNAME = "username"
    }

    fun saveUsername(username: String) {
        val editor = preferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return preferences.getString(KEY_USERNAME, null)
    }

    fun clearSession() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}
