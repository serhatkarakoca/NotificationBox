package com.karakoca.notificationbox.data.remote

import com.karakoca.notificationbox.data.local.room.NotificationDao
import com.karakoca.notificationbox.data.model.NotificationModel
import com.karakoca.notificationbox.data.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val dao: NotificationDao) :
    NotificationRepository {
    override suspend fun insertNotification(notificationModel: NotificationModel) {
        dao.insertNotification(notificationModel)
    }

    override fun getNotifications(): List<NotificationModel?>? {
        return dao.getNotifications()
    }

    override fun getNotificationsFlow(): Flow<List<NotificationModel?>?> {
        return dao.getNotificationsFlow()
    }

    override suspend fun removeNotification(id: Int) {
        dao.removeNotification(id)
    }

    override suspend fun removeAllNotifications() {
        dao.removeAllNotifications()
    }

    override suspend fun removeNotificationsByTitle(title: String) {
        dao.removeNotificationsByTitle(title)
    }
}