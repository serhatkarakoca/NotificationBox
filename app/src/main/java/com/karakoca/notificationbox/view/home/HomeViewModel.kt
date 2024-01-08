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
    private val _notifications = MutableSharedFlow<List<List<NotificationUI?>>>()
    val notifications: SharedFlow<List<List<NotificationUI?>>>
        get() = _notifications

    init {
        handleEvent(HomeEvent.GetNotifications)
    }

    private suspend fun getNotifications() {
        repo.getNotificationsFlow()
            .map {
                it?.map { it?.toNotificationUI() }
            }
            .collect {
                val finalValues = it?.reversed()?.groupBy { it?.title }?.values
                _notifications.emit(finalValues?.toList() ?: emptyList())
            }
    }

    private suspend fun deleteAllNotifications() {
        repo.removeAllNotifications()
    }

    fun handleEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetNotifications -> {
                    getNotifications()
                }

                is HomeEvent.DeleteAllNotifications -> {
                    deleteAllNotifications()
                }
            }
        }

    }
}

sealed interface HomeEvent {
    data object GetNotifications : HomeEvent
    data object DeleteAllNotifications : HomeEvent
}