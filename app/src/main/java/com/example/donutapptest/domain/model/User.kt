package com.example.donutapptest.domain.model

data class User(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val email: String,
    val lastLogin: String? = null
)
