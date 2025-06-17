package com.example.donutapptest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.donutapptest.domain.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun findUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE last_login IS NOT NULL ORDER BY last_login DESC LIMIT 1")
    suspend fun getMostRecentUser(): User?
}