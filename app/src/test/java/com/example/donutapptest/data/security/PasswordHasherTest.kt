package com.example.donutapptest.data.security

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.security.MessageDigest

class PasswordHasherTest {

    @Test
    fun `hash generates valid format with salt and hash separated by colon`() {
        val hash = PasswordHasher.hash("mySecretPassword")
        val parts = hash.split(":")
        assertEquals(2, parts.size)
        assertEquals(32, parts[0].length) // 16 bytes = 32 hex chars
        assertEquals(64, parts[1].length) // 32 bytes (256 bits) = 64 hex chars
    }

    @Test
    fun `hash generates different hashes for the same password due to random salt`() {
        val password = "samePassword123"
        val hash1 = PasswordHasher.hash(password)
        val hash2 = PasswordHasher.hash(password)

        assertNotEquals(hash1, hash2)
    }

    @Test
    fun `verify succeeds with correct password`() {
        val password = "StrongPassword#2026"
        val hash = PasswordHasher.hash(password)

        assertTrue(PasswordHasher.verify(password, hash))
    }

    @Test
    fun `verify fails with incorrect password`() {
        val password = "StrongPassword#2026"
        val hash = PasswordHasher.hash(password)

        assertFalse(PasswordHasher.verify("WrongPassword#2026", hash))
    }

    @Test
    fun `verify succeeds with legacy unsalted SHA-256 hash`() {
        val password = "legacyPassword"
        val digest = MessageDigest.getInstance("SHA-256")
        val legacyHash = digest.digest(password.toByteArray(Charsets.UTF_8)).joinToString("") { "%02x".format(it) }

        assertTrue(PasswordHasher.verify(password, legacyHash))
        assertFalse(PasswordHasher.verify("incorrectPassword", legacyHash))
    }
}
