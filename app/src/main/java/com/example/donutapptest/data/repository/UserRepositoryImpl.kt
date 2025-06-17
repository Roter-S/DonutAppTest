package com.example.donutapptest.data.repository

import com.example.donutapptest.data.local.UserDao
import com.example.donutapptest.domain.model.User
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {

    override suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    override suspend fun findUserByUsername(username: String): User? {
        return userDao.findUserByUsername(username)
    }

    override suspend fun checkUserPassword(username: String, password: String): Boolean {
        val user = userDao.findUserByUsername(username)
        return user?.password == password
    }

    override suspend fun isUserLoggedIn(): Boolean {
        val recentUser = userDao.getMostRecentUser()
        return recentUser?.lastLogin != null &&
                checkSessionValidity(recentUser.lastLogin)
    }

    override fun checkSessionValidity(lastLogin: String): Boolean {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val lastLoginTime = LocalDateTime.parse(lastLogin, formatter)
        val currentTime = LocalDateTime.now()

        return ChronoUnit.HOURS.between(lastLoginTime, currentTime) <= 24
    }

    override suspend fun isUserRegistered(email: String): Boolean {
        return userDao.findUserByUsername(email) != null
    }

    override suspend fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        val user = User(username = email, password = password)
        userDao.insertUser(user)
    }

    override suspend fun getMostRecentUser(): User? {
        return userDao.getMostRecentUser()
    }
}