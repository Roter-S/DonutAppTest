package com.example.donutapptest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.data.User
import com.example.donutapptest.data.UserDao
import com.example.donutapptest.data.UserDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao = UserDatabase.getDatabase(application).userDao()

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            userDao.insert(User(username = username, password = password))
        }
    }
}
