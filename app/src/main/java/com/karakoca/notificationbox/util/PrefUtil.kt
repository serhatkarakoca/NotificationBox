package com.karakoca.notificationbox.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.karakoca.notificationbox.data.model.NotificationModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PrefUtil {
    companion object {
        private const val NOTIFICATION_DATA = "NOTIFICATION_DATA"
        private const val TUTORIAL_PASSED = "TUTORIAL_PASSED"

        private lateinit var prefs: SharedPreferences
        private val json = Json { encodeDefaults = true }

        fun initSharedPrefs(context: Context): SharedPreferences {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs
        }

        fun setTutorialPassed() {
            prefs.edit().putBoolean(TUTORIAL_PASSED, true).apply()
        }

        fun getTutorialPassed(): Boolean {
            return prefs.getBoolean(TUTORIAL_PASSED, false)
        }

        fun setNotification(notification: NotificationModel) {
            val newList = arrayListOf<NotificationModel>()
            val recentNotifications = getNotifications()
            newList.addAll(recentNotifications)
            newList.add(notification)
            val jsonString = json.encodeToString<List<NotificationModel>>(newList)
            prefs.edit().putString(NOTIFICATION_DATA, jsonString).apply()
        }

        private fun getNotifications(): List<NotificationModel> {
            val data = prefs.getString(NOTIFICATION_DATA, "") ?: "[]"
            return json.decodeFromString<List<NotificationModel>>(data)
        }

    }
}