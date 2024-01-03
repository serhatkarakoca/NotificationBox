package com.karakoca.notificationbox.model.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karakoca.notificationbox.model.local.NotificationModel

@Database(
    entities = [NotificationModel::class],
    version = 1, exportSchema = true
)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

    companion object {

        @Volatile
        private var instance: NotificationDatabase? = null
        private val lock = Any()
        fun getDatabase(context: Context): NotificationDatabase {

            return instance ?: synchronized(lock) {
                instance ?: Room.databaseBuilder(
                    context,
                    NotificationDatabase::class.java,
                    "notification_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }

        }
    }
}