package com.example.donutapptest.domain.usecase.auth

import com.example.donutapptest.R
import com.example.donutapptest.core.common.Result
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.domain.model.User
import com.example.donutapptest.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User> {
        val trimmedEmail = email.trim()
        if (userRepository.isEmailRegistered(trimmedEmail)) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_user_already_registered)
            )
        }
        return userRepository.registerUser(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            email = trimmedEmail,
            password = password
        )
    }
}
