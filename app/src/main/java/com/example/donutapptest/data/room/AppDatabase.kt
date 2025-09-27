package com.example.donutapptest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.donutapptest.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}