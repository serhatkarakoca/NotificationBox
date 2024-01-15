package com.karakoca.notificationbox.data.repository

import com.karakoca.notificationbox.data.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun insertNotification(notificationModel: NotificationModel)
    fun getNotifications(): List<NotificationModel?>?
    fun getNotificationsFlow(): Flow<List<NotificationModel?>?>
    suspend fun removeNotification(id: Int)
    suspend fun removeAllNotifications()

    suspend fun removeNotificationsByTitle(title: String)
}