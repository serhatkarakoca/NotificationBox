package com.karakoca.notificationbox.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karakoca.notificationbox.data.model.NotificationUI
import com.karakoca.notificationbox.data.repository.NotificationRepository
import com.karakoca.notificationbox.util.toNotificationUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: NotificationRepository) : ViewModel() {
    private val _notifications = MutableSharedFlow<List<NotificationUI?>>()
    val notifications: SharedFlow<List<NotificationUI?>>
        get() = _notifications

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            repo.getNotificationsFlow()
                .map {
                    it?.map { it?.toNotificationUI() }
                }
                .collect {
                    _notifications.emit(it?.reversed() ?: emptyList())
                }

        }
    }
}