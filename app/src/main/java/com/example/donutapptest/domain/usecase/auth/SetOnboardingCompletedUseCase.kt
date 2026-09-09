package com.example.donutapptest.domain.usecase.auth

import com.example.donutapptest.domain.repository.UserRepository
import javax.inject.Inject

class SetOnboardingCompletedUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(completed: Boolean = true) {
        userRepository.setOnboardingCompleted(completed)
    }
}
