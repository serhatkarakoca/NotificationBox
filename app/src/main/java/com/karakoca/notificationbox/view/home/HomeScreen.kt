package com.karakoca.notificationbox.view.home

import android.content.Intent
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.karakoca.notificationbox.data.model.Constants
import com.karakoca.notificationbox.data.model.NotificationUI
import com.karakoca.notificationbox.util.AutoStartSetting
import com.karakoca.notificationbox.util.NotificationUtils
import com.karakoca.notificationbox.util.SystemBroadcastReceiver
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToDetails: (List<NotificationUI?>) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val status =
        NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.packageName)

    val notifications: List<List<NotificationUI?>> =
        viewModel.notifications.collectAsStateWithLifecycle(emptyList()).value

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var newData by remember { mutableStateOf(false) }

    val visibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }

    val manufacturer = Constants.brandList.firstOrNull {
        it.lowercase().equals(Build.MANUFACTURER, ignoreCase = true)
    }

    val dialogState = remember { mutableStateOf(false) }

    /*
    LaunchedEffect(key1 = Unit, block = {
        if (manufacturer != null)
            dialogState.value = true
    })

     */


    if (dialogState.value && manufacturer != null) {
        AlertDialog(onDismissRequest = { dialogState.value = false }, confirmButton = {
            Button(onClick = {
                dialogState.value = false
                AutoStartSetting.startAutoStartDialog(context, manufacturer)
            }) {
                Text(text = "kapat")
            }
        })
    }

    SystemBroadcastReceiver(
        systemAction = Constants.INTENT_ACTION_NOTIFICATION,
        onSystemEvent = { intent ->
            if (intent != null) {
                val extras = intent.extras
                val updateData = extras?.getBoolean("AnyNew")
                if (updateData == true)
                    newData = visibleItemIndex.value > 1
            }
        })


    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = newData, modifier = Modifier
                .padding(55.dp)
                .align(Alignment.TopCenter)
                .zIndex(10f)
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                        newData = false
                    }

                }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 2.dp),
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
                Text(text = "New Notifications")
            }
        }


        Column {


            Text(text = "Delete All Notifications", modifier = Modifier
                .padding(24.dp)
                .clickable {
                    /*
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                startActivity(context, intent, null)

                 */
                    viewModel.handleEvent(HomeEvent.DeleteAllNotifications)
                })

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
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                state = listState
            ) {
                items(items = notifications ?: emptyList()) {
                    it.let { itemList ->
                        itemList.lastOrNull()?.let {
                            NotificationItem(
                                item = it,
                                size = itemList.size.toString(),
                                clickListener = { title ->
                                    val list =
                                        notifications.firstOrNull { it.firstOrNull { it?.title == title } != null }
                                            ?: emptyList()

                                    navigateToDetails.invoke(list)
                                })
                        }
                    }
                }
            }
        }
    }
}