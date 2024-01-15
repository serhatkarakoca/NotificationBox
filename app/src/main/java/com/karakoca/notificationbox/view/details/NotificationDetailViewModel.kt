package com.karakoca.notificationbox.view.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.karakoca.notificationbox.data.model.Constants.NOTIFICATION_ITEM
import com.karakoca.notificationbox.data.model.NotificationUI
import com.karakoca.notificationbox.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationDetailViewModel @Inject constructor(
    private val gson: Gson,
    private val repository: NotificationRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    var notifications: List<NotificationUI> = emptyList()
        private set

    init {
        savedStateHandle.get<String>(NOTIFICATION_ITEM)?.let {
            try {
                Log.d("NOTIFICATION_DETAIL_ERROR", it.toString())
                val newNotificationList = arrayListOf<NotificationUI>()
                val notificationList = gson.fromJson<List<NotificationUI>>(
                    it,
                    object : TypeToken<ArrayList<NotificationUI>>() {}.type
                ) ?: emptyList()
                val notificationMap = notificationList.groupBy { it.date?.substringBefore(" ") }
                notificationMap.forEach { (date, value) ->
                    newNotificationList.add(
                        NotificationUI(
                            isOnlyDate = true, date = date ?: "",
                            id = 0,
                            title = null,
                            text = null,
                            icon = null,
                            `when` = value.firstOrNull()?.`when` ?: 0,
                            messageImage = null,
                            packageName = null,
                            expanded = false,
                            color = 0
                        )
                    )
                    newNotificationList.addAll(value)

                }
                notifications = newNotificationList

            } catch (e: Exception) {
                Log.e("NOTIFICATION_DETAIL_ERROR", e.stackTraceToString().toString())
            }
        }
    }

    fun removeHistory() {
        notifications.lastOrNull()?.title?.let { title ->
            viewModelScope.launch {
                repository.removeNotificationsByTitle(title)
            }
        }
    }
}