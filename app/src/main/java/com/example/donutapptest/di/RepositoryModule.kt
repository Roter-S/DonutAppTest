package com.example.donutapptest.di

import com.example.donutapptest.data.repository.DonutRepositoryImpl
import com.example.donutapptest.data.repository.UserRepositoryImpl
import com.example.donutapptest.domain.repository.DonutRepository
import com.example.donutapptest.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindDonutRepository(donutRepositoryImpl: DonutRepositoryImpl): DonutRepository
}