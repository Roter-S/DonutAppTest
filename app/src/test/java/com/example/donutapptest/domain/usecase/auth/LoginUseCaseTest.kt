package com.example.donutapptest.domain.usecase.auth

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.data.repository.FakeUserRepository
import com.example.donutapptest.domain.model.User
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        loginUseCase = LoginUseCase(fakeUserRepository)
    }

    @Test
    fun `when credentials are valid then login succeeds and returns user`() = runTest {
        val user = User(id = 1, firstName = "Jane", lastName = "Doe", email = "jane@example.com")
        fakeUserRepository.addUser(user, "password123")

        val result = loginUseCase("jane@example.com", "password123")

        assertTrue(result is Result.Success)
        assertEquals(user, (result as Result.Success).data)
    }

    @Test
    fun `when credentials are invalid then login returns error`() = runTest {
        val user = User(id = 1, firstName = "Jane", lastName = "Doe", email = "jane@example.com")
        fakeUserRepository.addUser(user, "password123")

        val result = loginUseCase("jane@example.com", "wrongPassword")

        assertTrue(result is Result.Error)
    }

    @Test
    fun `when email has whitespace then it is trimmed and authenticates successfully`() = runTest {
        val user = User(id = 1, firstName = "Jane", lastName = "Doe", email = "jane@example.com")
        fakeUserRepository.addUser(user, "password123")

        val result = loginUseCase("  jane@example.com  ", "password123")

        assertTrue(result is Result.Success)
    }
}
