package com.example.donutapptest.data.repository

import com.example.donutapptest.domain.model.User

class FakeUserRepository : UserRepository {
    private val users = mutableListOf<User>()

    override suspend fun insertUser(user: User): Long = 1L

    override suspend fun findUserByUsername(username: String): User? = null

    override suspend fun checkUserPassword(username: String, password: String): Boolean = false

    override suspend fun isUserLoggedIn(): Boolean = false

    override fun checkSessionValidity(lastLogin: String): Boolean = false

    override suspend fun isUserRegistered(email: String): Boolean {
        return users.any { it.username == email }
    }

    override suspend fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        users.add(User(username = email, password = password))
    }

    override suspend fun getMostRecentUser(): User? = users.lastOrNull()
}