package com.karakoca.notificationbox

import android.app.Application
import com.karakoca.notificationbox.model.local.room.NotificationDatabase
import com.karakoca.notificationbox.util.PrefUtil

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        PrefUtil.initSharedPrefs(this)
        NotificationDatabase.getDatabase(this)
    }
}