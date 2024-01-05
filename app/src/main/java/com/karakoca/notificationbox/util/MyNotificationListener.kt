package com.karakoca.notificationbox.util

import android.app.Notification
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.karakoca.notificationbox.model.local.Constants.INTENT_ACTION_NOTIFICATION
import com.karakoca.notificationbox.model.local.NotificationModel
import com.karakoca.notificationbox.model.local.room.NotificationDao
import com.karakoca.notificationbox.model.local.room.NotificationDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class MyNotificationListener : NotificationListenerService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var dao: NotificationDao? = null

    override fun onBind(intent: Intent?): IBinder? {
        NotificationDatabase.initDatabase(this)
        dao = NotificationDatabase.getDatabase().notificationDao()
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val mNotification = sbn.notification
        if (mNotification != null && sbn.key.contains("null").not()) {
            val intent = Intent(INTENT_ACTION_NOTIFICATION)
            var image: Bitmap? = null
            var appIcon: Bitmap? = null
            val extras = mNotification.extras

            val notificationTitle = extras?.getString(Notification.EXTRA_TITLE)
            val notificationText = extras?.getCharSequence(Notification.EXTRA_TEXT)
            val notificationSubText = extras?.getCharSequence(Notification.EXTRA_SUB_TEXT)
            intent.putExtra("AnyNew", true)
            sendBroadcast(intent)

            // bildirimdeki resim
            try {
                if (extras.containsKey(Notification.EXTRA_PICTURE)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        image =
                            extras?.getParcelable(Notification.EXTRA_PICTURE, Bitmap::class.java)
                    } else
                        image = extras.get(Notification.EXTRA_PICTURE) as? Bitmap
                }
            } catch (e: Exception) {
                Log.e("ERROR_NOTIFICATION_SERVICE", e.message.toString())
            }

            // gelen uygulama ikonu

            val drawable = extras?.getInt("android.icon")?.let {
                return@let try {
                    this.packageManager.getApplicationIcon(sbn.packageName)
                } catch (_: Exception) {
                    null
                }
            }


            if (drawable is BitmapDrawable) {
                appIcon = drawable.bitmap
            } else if (drawable != null) {
                val bitmapDrawable = BitmapDrawable(resources, drawable.toBitmap())
                appIcon = bitmapDrawable.bitmap
            }

            scope.launch {
                val lastNotification = dao?.getNotifications()?.lastOrNull()
                if (sbn.notification.`when` != lastNotification?.`when` && notificationText != null && notificationTitle != null)
                    dao?.insertNotification(
                        NotificationModel(
                            title = notificationTitle, text = notificationText.toString(),
                            icon = if (appIcon != null) convertToBase64(appIcon) else null,
                            messageImage = if (image != null) convertToBase64(image) else null,
                            `when` = sbn.notification.`when`,
                            date = convertLongToTime(sbn.notification.`when`),
                            packageName = sbn.packageName
                        )
                    )
            }

        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}