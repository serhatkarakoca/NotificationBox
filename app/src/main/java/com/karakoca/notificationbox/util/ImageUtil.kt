package com.karakoca.notificationbox.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import com.karakoca.notificationbox.ui.theme.PastelBlue
import com.karakoca.notificationbox.ui.theme.PastelGreen
import com.karakoca.notificationbox.ui.theme.PastelOrange
import com.karakoca.notificationbox.ui.theme.PastelPurple
import com.karakoca.notificationbox.ui.theme.PastelRed
import java.io.ByteArrayOutputStream
import kotlin.random.Random


fun convertToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun convertBase64ToBitmap(base64: String): Bitmap? {
    val byteArray = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun generatePastelColor(): Int {
    val rnd = Random(System.currentTimeMillis())

    // Rastgele renk oluştur
    val originalColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

    // Pastel tonlara dönüştür
    val pastelFactor = 0.7 // Ayarlanabilir bir faktör
    val pastelRed = (Color.red(originalColor) + 255) / 2
    val pastelGreen = (Color.green(originalColor) + 255) / 2
    val pastelBlue = (Color.blue(originalColor) + 255) / 2

    return Color.argb(255, pastelRed, pastelGreen, pastelBlue)
}

fun randomLocalColor(): androidx.compose.ui.graphics.Color {
    val random = Random.nextInt(0, 6)
    return listOf(
        PastelRed,
        PastelGreen,
        PastelPurple,
        PastelOrange,
        PastelBlue,
        androidx.compose.ui.graphics.Color.Black
    ).get(random)
}