package com.example.donutapptest.domain.usecase.auth

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.domain.model.User
import com.example.donutapptest.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(emailOrUsername: String, password: String): Result<User> {
        return userRepository.authenticate(emailOrUsername.trim(), password)
    }
}
