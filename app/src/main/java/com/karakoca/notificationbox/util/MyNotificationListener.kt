package com.karakoca.notificationbox.util

import android.app.Notification
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.karakoca.notificationbox.data.model.Constants.INTENT_ACTION_NOTIFICATION
import com.karakoca.notificationbox.data.model.NotificationModel
import com.karakoca.notificationbox.data.repository.NotificationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@AndroidEntryPoint
class MyNotificationListener : NotificationListenerService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var repo: NotificationRepository

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val mNotification = sbn.notification

        if (mNotification != null && sbn.tag != null) {
            val intent = Intent(INTENT_ACTION_NOTIFICATION)
            var image: Bitmap? = null
            var appIcon: Bitmap? = null
            val extras = mNotification.extras

            val notificationTitle = extras?.getString(Notification.EXTRA_TITLE, null)?.toString()
            val notificationText = extras?.getCharSequence(Notification.EXTRA_TEXT)?.toString()
            val notificationSubText = extras?.getCharSequence(Notification.EXTRA_SUB_TEXT)
            val textLines = extras?.getCharSequenceArray(Notification.EXTRA_TEXT_LINES)
            if (textLines.isNullOrEmpty())
                println(notificationTitle)
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
                val lastNotification = repo.getNotifications()?.lastOrNull()
                val isNewNotification =
                    Calendar.getInstance().timeInMillis - sbn.notification.`when` < 1000
                if (sbn.notification.`when` != lastNotification?.`when` && notificationText != null && notificationTitle != null && textLines.isNullOrEmpty() && isNewNotification)
                    repo.insertNotification(
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