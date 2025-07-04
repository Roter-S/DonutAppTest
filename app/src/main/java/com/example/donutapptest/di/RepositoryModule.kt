package com.example.donutapptest.di

import com.example.donutapptest.data.repository.DonutRepository
import com.example.donutapptest.data.repository.DonutRepositoryImpl
import com.example.donutapptest.data.repository.UserRepository
import com.example.donutapptest.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindDonutRepository(
        impl: DonutRepositoryImpl
    ): DonutRepository
}