package com.karakoca.notificationbox.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.karakoca.notificationbox.R
import com.karakoca.notificationbox.data.model.Constants
import com.karakoca.notificationbox.data.model.NotificationUI
import com.karakoca.notificationbox.util.SystemBroadcastReceiver
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navigateToDetails: (List<NotificationUI?>) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val notifications: List<List<NotificationUI?>> =
        viewModel.notifications.collectAsStateWithLifecycle(emptyList()).value

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var newData by remember { mutableStateOf(false) }

    val visibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val filterVisible = remember { mutableStateOf(false) }
    val sortNewest = remember {
        mutableStateOf(true)
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
                Text(text = stringResource(id = R.string.new_notifications))
            }
        }


        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.surface
                )

                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "back",
                    modifier = Modifier.clickable {
                        filterVisible.value = !filterVisible.value
                    },
                    tint = MaterialTheme.colorScheme.surface
                )
            }
            AnimatedVisibility(
                visible = filterVisible.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Column {

                    Text(
                        text = stringResource(R.string.sort_by),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.padding(start = 16.dp),
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            sortNewest.value = true
                        }
                    ) {
                        RadioButton(
                            selected = sortNewest.value,
                            onClick = {
                                sortNewest.value = true
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.White,
                                unselectedColor = Color.White
                            )
                        )
                        Text(
                            text = stringResource(R.string.newest_first),
                            color = MaterialTheme.colorScheme.surface,
                            fontSize = 12.sp
                        )
                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable {
                                    sortNewest.value = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = !sortNewest.value,
                                onClick = {
                                    sortNewest.value = false
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.White,
                                    unselectedColor = Color.White
                                )
                            )
                            Text(
                                text = stringResource(R.string.oldest_first),
                                color = MaterialTheme.colorScheme.surface,
                                fontSize = 12.sp
                            )
                        }
                    }


                }

            }


            LazyColumn(
                Modifier
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                state = listState
            ) {
                items(items = notifications) {
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