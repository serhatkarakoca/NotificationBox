package com.karakoca.notificationbox.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.karakoca.notificationbox.data.model.NotificationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert
    suspend fun insertNotification(notificationModel: NotificationModel)

    @Query("SELECT * FROM notification")
    fun getNotifications(): List<NotificationModel?>?

    @Query("SELECT * FROM notification")
    fun getNotificationsFlow(): Flow<List<NotificationModel?>?>

    @Query("DELETE FROM notification WHERE id = :id")
    suspend fun removeNotification(id: Int)

    @Query("DELETE FROM notification")
    suspend fun removeAllNotifications()
}