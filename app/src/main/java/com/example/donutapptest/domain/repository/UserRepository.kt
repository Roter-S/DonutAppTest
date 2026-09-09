package com.example.donutapptest.domain.repository

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun authenticate(email: String, password: String): Result<User>
    suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User>
    suspend fun isEmailRegistered(email: String): Boolean
    suspend fun getRecentUser(): User?
    fun isSessionActive(): Flow<Boolean>
    fun getActiveUsername(): Flow<String?>
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun logout()
}
