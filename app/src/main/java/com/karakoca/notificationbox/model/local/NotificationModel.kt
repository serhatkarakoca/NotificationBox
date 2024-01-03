package com.karakoca.notificationbox.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity("notification")
data class NotificationModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val text: String?,
    val icon: String?,
    val `when`: Long?,
    val date: String?,
    val messageImage: String?,
    val packageName: String?,
    var expanded: Boolean = false
)
