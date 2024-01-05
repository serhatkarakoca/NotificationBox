package com.karakoca.notificationbox.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karakoca.notificationbox.model.NotificationUI
import com.karakoca.notificationbox.model.local.room.NotificationDatabase
import com.karakoca.notificationbox.util.toNotificationUI
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val dao = NotificationDatabase.getDatabase().notificationDao()
    private val _notifications = MutableSharedFlow<List<NotificationUI?>>()
    val notifications: SharedFlow<List<NotificationUI?>>
        get() = _notifications

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            dao.getNotificationsFlow()
                .map {
                    it?.map { it?.toNotificationUI() }
                }
                .collect {
                    _notifications.emit(it?.reversed() ?: emptyList())
                }
        }
    }
}