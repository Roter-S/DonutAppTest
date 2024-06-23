package com.example.donutapptest.data

interface ItemsUser {

    suspend fun insert(user: User)
}


class UserRepository(private val userDao: UserDao) : ItemsUser {
    override suspend fun insert(user: User) = userDao.insert(user)

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun authenticate(username: String, password: String): Boolean {
        val user = userDao.getUserByUsername(username)
        return user?.password == password
    }
}
