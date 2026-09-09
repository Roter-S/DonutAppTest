package com.example.donutapptest.data.mapper

import com.example.donutapptest.data.local.entity.UserEntity
import com.example.donutapptest.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = username,
        lastLogin = lastLogin
    )
}

fun User.toEntity(passwordHash: String): UserEntity {
    return UserEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        username = email,
        passwordHash = passwordHash,
        lastLogin = lastLogin
    )
}
