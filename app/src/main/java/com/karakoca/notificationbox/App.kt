package com.karakoca.notificationbox

import android.app.Application
import android.content.Intent
import com.karakoca.notificationbox.util.MyNotificationListener
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startService(Intent(this, MyNotificationListener::class.java))
    }
}