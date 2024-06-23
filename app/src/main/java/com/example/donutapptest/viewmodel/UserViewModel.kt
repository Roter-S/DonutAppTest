package com.example.donutapptest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.data.User
import com.example.donutapptest.data.UserDao
import com.example.donutapptest.data.UserDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao = UserDatabase.getDatabase(application).userDao()
    private val _showAlert = MutableStateFlow<String?>(null)

    val showAlert: StateFlow<String?> get() = _showAlert

    fun registerUser(username: String, password: String, registerSuccess: String) {
        viewModelScope.launch {
            userDao.insert(User(username = username, password = password))
            _showAlert.value = registerSuccess
        }
    }

    fun clearAlert() {
        _showAlert.value = null
    }
}

