package com.example.donutapptest.domain.usecase.auth

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidateUsernameUseCaseTest {

    private lateinit var validateUsernameUseCase: ValidateUsernameUseCase

    @Before
    fun setUp() {
        validateUsernameUseCase = ValidateUsernameUseCase()
    }

    @Test
    fun `when username length is less than 8 then validation fails`() {
        val result = validateUsernameUseCase("short")
        assertFalse(result.successful)
        assertNotNull(result.errorMessage)
    }

    @Test
    fun `when username length is 8 or more then validation succeeds`() {
        val result = validateUsernameUseCase("username123")
        assertTrue(result.successful)
        assertNull(result.errorMessage)
    }
}
