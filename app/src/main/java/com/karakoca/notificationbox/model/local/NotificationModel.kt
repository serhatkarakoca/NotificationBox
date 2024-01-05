package com.karakoca.notificationbox.model.local

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.karakoca.notificationbox.util.randomLocalColor
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@Entity("notification")
data class NotificationModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val text: String?,
    val icon: String?,
    val `when`: Long,
    var date: String?,
    val messageImage: String?,
    val packageName: String?,
    val color: Int = randomLocalColor().toArgb()
)
