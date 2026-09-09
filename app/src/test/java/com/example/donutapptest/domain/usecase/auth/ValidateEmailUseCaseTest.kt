package com.example.donutapptest.domain.usecase.auth

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun `when email is blank then validation fails`() {
        val result = validateEmailUseCase("")
        assertFalse(result.successful)
        assertNotNull(result.errorMessage)
    }

    @Test
    fun `when email has invalid format then validation fails`() {
        val result = validateEmailUseCase("invalid_email")
        assertFalse(result.successful)
        assertNotNull(result.errorMessage)
    }

    @Test
    fun `when email lacks domain then validation fails`() {
        val result = validateEmailUseCase("test@")
        assertFalse(result.successful)
        assertNotNull(result.errorMessage)
    }

    @Test
    fun `when email has valid format then validation succeeds`() {
        val result = validateEmailUseCase("user@example.com")
        assertTrue(result.successful)
        assertNull(result.errorMessage)
    }
}
