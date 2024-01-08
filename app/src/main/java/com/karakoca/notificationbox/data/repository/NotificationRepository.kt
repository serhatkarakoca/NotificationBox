package com.karakoca.notificationbox.data.repository

import com.karakoca.notificationbox.data.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun insertNotification(notificationModel: NotificationModel)
    fun getNotifications(): List<NotificationModel?>?
    fun getNotificationsFlow(): Flow<List<NotificationModel?>?>
    fun removeNotification(id: Int)
    fun removeAllNotifications()
}