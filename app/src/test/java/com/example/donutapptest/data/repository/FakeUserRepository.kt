package com.example.donutapptest.data.repository

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.domain.model.User
import com.example.donutapptest.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeUserRepository : UserRepository {

    private val users = mutableMapOf<String, Pair<User, String>>()
    private val _isSessionActive = MutableStateFlow(false)
    private val _activeUsername = MutableStateFlow<String?>(null)
    private val _isOnboardingCompleted = MutableStateFlow(false)

    var returnErrorOnAuthenticate: Boolean = false
    var returnErrorOnRegister: Boolean = false

    override suspend fun authenticate(email: String, password: String): Result<User> {
        if (returnErrorOnAuthenticate) {
            return Result.Error(message = UiText.DynamicString("Invalid credentials"))
        }
        val entry = users[email]
        return if (entry != null && entry.second == password) {
            _isSessionActive.value = true
            _activeUsername.value = email
            Result.Success(entry.first)
        } else {
            Result.Error(message = UiText.DynamicString("Invalid credentials"))
        }
    }

    override suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User> {
        if (returnErrorOnRegister) {
            return Result.Error(message = UiText.DynamicString("Registration failed"))
        }
        val user = User(
            id = users.size + 1,
            firstName = firstName,
            lastName = lastName,
            email = email,
            lastLogin = "2026-09-08T00:00:00Z"
        )
        users[email] = Pair(user, password)
        _isSessionActive.value = true
        _activeUsername.value = email
        return Result.Success(user)
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return users.containsKey(email)
    }

    override suspend fun getRecentUser(): User? {
        return users.values.lastOrNull()?.first
    }

    override fun isSessionActive(): Flow<Boolean> = _isSessionActive.asStateFlow()

    override fun getActiveUsername(): Flow<String?> = _activeUsername.asStateFlow()

    override fun isOnboardingCompleted(): Flow<Boolean> = _isOnboardingCompleted.asStateFlow()

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        _isOnboardingCompleted.value = completed
    }

    override suspend fun logout() {
        _isSessionActive.value = false
        _activeUsername.value = null
    }

    fun addUser(user: User, password: String) {
        users[user.email] = Pair(user, password)
    }

    fun setInitialSession(active: Boolean, username: String? = null) {
        _isSessionActive.value = active
        _activeUsername.value = username
    }
}
