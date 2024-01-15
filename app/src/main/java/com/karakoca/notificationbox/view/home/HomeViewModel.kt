package com.karakoca.notificationbox.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karakoca.notificationbox.data.model.NotificationUI
import com.karakoca.notificationbox.data.repository.NotificationRepository
import com.karakoca.notificationbox.util.toNotificationUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: NotificationRepository) : ViewModel() {
    private val _notifications = MutableStateFlow<List<List<NotificationUI?>>>(emptyList())
    val notifications: StateFlow<List<List<NotificationUI?>>>
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
                val finalValues =
                    it?.groupBy { it?.title }?.values?.sortedBy { it.lastOrNull()?.`when` }
                        ?.reversed()
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