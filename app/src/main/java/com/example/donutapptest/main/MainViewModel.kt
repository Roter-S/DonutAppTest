package com.example.donutapptest.main

import androidx.lifecycle.ViewModel
import com.example.donutapptest.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun isUserLoggedIn(): Boolean {
        return userRepository.isUserLoggedIn()
    }
}