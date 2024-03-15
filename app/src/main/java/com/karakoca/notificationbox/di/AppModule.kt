package com.karakoca.notificationbox.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.karakoca.notificationbox.data.local.room.NotificationDatabase
import com.karakoca.notificationbox.util.PrefUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePrefUtil(@ApplicationContext context: Context): PrefUtil {
        return PrefUtil(context)
    }

    @Provides
    @Singleton
    fun provideNotificationDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        NotificationDatabase::class.java,
        "notification_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(db: NotificationDatabase) = db.notificationDao()


    @Provides
    @Singleton
    fun gson() = Gson()

}