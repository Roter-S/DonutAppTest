package com.example.donutapptest.data.repository

import com.example.donutapptest.domain.model.User

class FakeUserRepository : UserRepository {
    override suspend fun insertUser(user: User): Long = 1L

    override suspend fun findUserByUsername(username: String): User? = null

    override suspend fun checkUserPassword(username: String, password: String): Boolean = false

    override suspend fun isUserLoggedIn(): Boolean = false

    override fun checkSessionValidity(lastLogin: String): Boolean = false
}