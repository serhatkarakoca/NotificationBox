package com.karakoca.notificationbox.util

import com.karakoca.notificationbox.model.NotificationUI
import com.karakoca.notificationbox.model.local.NotificationModel

fun NotificationModel.toNotificationUI(): NotificationUI {
    val icon = icon?.let { convertBase64ToBitmap(it) }
    val image = messageImage?.let { convertBase64ToBitmap(it) }
    return NotificationUI(id, title, text, icon, `when`, date, image, packageName, false, color)
}