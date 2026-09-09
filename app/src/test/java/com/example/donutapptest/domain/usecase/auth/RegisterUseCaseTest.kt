package com.example.donutapptest.domain.usecase.auth

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.data.repository.FakeUserRepository
import com.example.donutapptest.domain.model.User
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RegisterUseCaseTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        registerUseCase = RegisterUseCase(fakeUserRepository)
    }

    @Test
    fun `when email is not registered then registration succeeds`() = runTest {
        val result = registerUseCase(
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            password = "password123"
        )

        assertTrue(result is Result.Success)
        val user = (result as Result.Success).data
        assertEquals("John", user.firstName)
        assertEquals("Doe", user.lastName)
        assertEquals("john@example.com", user.email)
    }

    @Test
    fun `when email is already registered then returns error`() = runTest {
        val existingUser = User(id = 1, firstName = "Jane", lastName = "Doe", email = "jane@example.com")
        fakeUserRepository.addUser(existingUser, "password123")

        val result = registerUseCase(
            firstName = "Another",
            lastName = "User",
            email = "jane@example.com",
            password = "password456"
        )

        assertTrue(result is Result.Error)
    }

    @Test
    fun `when inputs have whitespace then they are trimmed upon registration`() = runTest {
        val result = registerUseCase(
            firstName = "  John  ",
            lastName = "  Doe  ",
            email = "  john.trimmed@example.com  ",
            password = "password123"
        )

        assertTrue(result is Result.Success)
        val user = (result as Result.Success).data
        assertEquals("John", user.firstName)
        assertEquals("Doe", user.lastName)
        assertEquals("john.trimmed@example.com", user.email)
    }
}
