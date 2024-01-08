package com.karakoca.notificationbox.data.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.toArgb
import com.karakoca.notificationbox.util.randomLocalColor

data class NotificationUI(
    val id: Int = 0,
    val title: String?,
    val text: String?,
    val icon: Bitmap?,
    val `when`: Long,
    var date: String?,
    val messageImage: Bitmap?,
    val packageName: String?,
    var expanded: Boolean = false,
    val color: Int = randomLocalColor().toArgb()
)
