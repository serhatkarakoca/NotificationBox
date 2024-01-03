package com.karakoca.notificationbox.view.home

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import com.karakoca.notificationbox.model.local.Constants
import com.karakoca.notificationbox.model.local.room.NotificationDatabase
import com.karakoca.notificationbox.util.AutoStartSetting
import com.karakoca.notificationbox.util.NotificationUtils
import com.karakoca.notificationbox.util.SystemBroadcastReceiver
import com.karakoca.notificationbox.util.convertBase64ToBitmap


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val status =
        NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.packageName)

    val notifications =
        NotificationDatabase.getDatabase(context).notificationDao().getNotificationsFlow()
            .collectAsState(
                initial = null
            )


    val manufacturer =
        Constants.brandList.firstOrNull {
            it.lowercase().equals(Build.MANUFACTURER, ignoreCase = true)
        }

    val dialogState = remember {
        mutableStateOf(false)
    }

    if (manufacturer != null)
        dialogState.value = true

    if (dialogState.value && manufacturer != null)
        AlertDialog(onDismissRequest = { dialogState.value = false }, confirmButton = {
            Button(onClick = {
                dialogState.value = false
                AutoStartSetting.startAutoStartDialog(context, manufacturer)

            }) {
                Text(text = "kapat")
            }
        })


    SystemBroadcastReceiver(
        systemAction = Constants.INTENT_ACTION_NOTIFICATION,
        onSystemEvent = { intent ->
            if (intent != null) {
                val extras = intent.extras
                val updateData = extras?.getBoolean("AnyNew")


            }
        })


    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            Text(text = "Home Screen", modifier = Modifier.clickable {
                /*
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                startActivity(context, intent, null)

                 */

                NotificationUtils.createTestNotification(context)
            })


            Text(text = "Enable Notification Permission", Modifier.clickable {
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                startActivity(context, intent, null)
            })



            LazyColumn(
                Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                items(items = notifications.value ?: emptyList(), key = { it?.id ?: 0 }) {
                    it?.let {
                        var icon: Bitmap? = null
                        it?.icon?.let { it1 -> icon = convertBase64ToBitmap(it1) }
                        val expanded = remember {
                            mutableStateOf(it.expanded)
                        }


                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable { expanded.value = !expanded.value }) {
                            icon?.asImageBitmap()
                                ?.let { it1 ->
                                    Image(
                                        bitmap = it1,
                                        contentDescription = null,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }

                            Text(text = it.title.toString(), modifier = Modifier.fillMaxWidth())

                        }
                        AnimatedVisibility(visible = expanded.value) {
                            Text(text = it.text.toString(), modifier = Modifier.fillMaxWidth())
                        }
                        Text(text = it.date.toString(), modifier = Modifier.fillMaxWidth())
                        Divider()
                    }
                }
            }

        }
    }
}