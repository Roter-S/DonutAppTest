package com.example.donutapptest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.donutapptest.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}