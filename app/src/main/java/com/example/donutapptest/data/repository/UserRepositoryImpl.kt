package com.example.donutapptest.data.repository

import com.example.donutapptest.R
import com.example.donutapptest.core.common.Result
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.data.local.entity.UserEntity
import com.example.donutapptest.data.mapper.toDomain
import com.example.donutapptest.data.room.UserDao
import com.example.donutapptest.data.session.SessionManager
import com.example.donutapptest.data.security.PasswordHasher
import com.example.donutapptest.domain.model.User
import com.example.donutapptest.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) : UserRepository {

    override suspend fun authenticate(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        val userEntity = userDao.findUserByUsername(email)
            ?: return@withContext Result.Error(
                message = UiText.StringResource(R.string.error_message_auth_invalid_credentials)
            )

        if (!PasswordHasher.verify(password, userEntity.passwordHash)) {
            return@withContext Result.Error(
                message = UiText.StringResource(R.string.error_message_auth_invalid_credentials)
            )
        }

        val updatedEntity = userEntity.copy(lastLogin = Instant.now().toString())
        userDao.insertUser(updatedEntity)

        sessionManager.setLoggedIn(true)
        sessionManager.setUsername(email)

        Result.Success(updatedEntity.toDomain())
    }

    override suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User> = withContext(Dispatchers.IO) {
        val hash = PasswordHasher.hash(password)
        val now = Instant.now().toString()

        val entity = UserEntity(
            firstName = firstName,
            lastName = lastName,
            username = email,
            passwordHash = hash,
            lastLogin = now
        )

        userDao.insertUser(entity)
        sessionManager.setLoggedIn(true)
        sessionManager.setUsername(email)

        Result.Success(entity.toDomain())
    }

    override suspend fun isEmailRegistered(email: String): Boolean = withContext(Dispatchers.IO) {
        userDao.findUserByUsername(email) != null
    }

    override suspend fun getRecentUser(): User? = withContext(Dispatchers.IO) {
        userDao.getMostRecentUser()?.toDomain()
    }

    override fun isSessionActive(): Flow<Boolean> {
        return sessionManager.isLoggedIn
    }

    override fun getActiveUsername(): Flow<String?> {
        return sessionManager.username
    }

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return sessionManager.isOnboardingCompleted
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        sessionManager.setOnboardingCompleted(completed)
    }

    override suspend fun logout() {
        sessionManager.clearSession()
    }
}
