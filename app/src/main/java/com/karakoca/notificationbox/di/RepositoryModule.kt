package com.karakoca.notificationbox.di

import com.karakoca.notificationbox.data.remote.RepositoryImpl
import com.karakoca.notificationbox.data.repository.NotificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): NotificationRepository
}