package com.example.donutapptest.domain.usecase.auth

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase

    @Before
    fun setUp() {
        validatePasswordUseCase = ValidatePasswordUseCase()
    }

    @Test
    fun `when password length is less than 6 then validation fails`() {
        val result = validatePasswordUseCase("12345")
        assertFalse(result.successful)
        assertNotNull(result.errorMessage)
    }

    @Test
    fun `when complexity is required and password lacks uppercase then validation fails`() {
        val result = validatePasswordUseCase("abcdef", requireComplexity = true)
        assertFalse(result.successful)
        assertNotNull(result.errorMessage)
    }

    @Test
    fun `when complexity is required and password has uppercase then validation succeeds`() {
        val result = validatePasswordUseCase("Abcdef", requireComplexity = true)
        assertTrue(result.successful)
        assertNull(result.errorMessage)
    }

    @Test
    fun `when complexity is not required and length is 6 then validation succeeds`() {
        val result = validatePasswordUseCase("abcdef", requireComplexity = false)
        assertTrue(result.successful)
        assertNull(result.errorMessage)
    }
}
