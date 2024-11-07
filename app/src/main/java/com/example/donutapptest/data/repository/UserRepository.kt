package com.example.donutapptest.data.repository

import com.example.donutapptest.domain.model.User

interface UserRepository {
    suspend fun insertUser(user: User): Long
    suspend fun findUserByUsername(username: String): User?
    suspend fun checkUserPassword(username: String, password: String): Boolean
    suspend fun isUserLoggedIn(): Boolean
    fun checkSessionValidity(lastLogin: String): Boolean
}