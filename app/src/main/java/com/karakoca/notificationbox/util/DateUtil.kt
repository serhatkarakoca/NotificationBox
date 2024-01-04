package com.karakoca.notificationbox.util

import com.karakoca.notificationbox.model.local.Constants.TODAY
import com.karakoca.notificationbox.model.local.Constants.YESTERDAY
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}

fun getDateString(time: Long): String {
    val date = Date(time)
    val today = Date()

    return when {
        isSameDay(date, today) -> TODAY
        isYesterday(date, today) -> YESTERDAY
        else -> convertLongToTime(time)
    }

}

fun isSameDay(date1: Date, date2: Date): Boolean {
    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    return dateFormat.format(date1) == dateFormat.format(date2)
}

fun isYesterday(date: Date, today: Date): Boolean {
    val yesterday = Date(today.time - TimeUnit.DAYS.toMillis(1))
    return isSameDay(date, yesterday)
}