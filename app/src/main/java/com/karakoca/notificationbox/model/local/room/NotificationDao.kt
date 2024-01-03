package com.karakoca.notificationbox.model.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.karakoca.notificationbox.model.local.NotificationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert
    suspend fun insertNotification(notificationModel: NotificationModel)

    @Query("SELECT * FROM notification")
    fun getNotifications(): List<NotificationModel?>?

    @Query("SELECT * FROM notification")
    fun getNotificationsFlow(): Flow<List<NotificationModel?>?>

}