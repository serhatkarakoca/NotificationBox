package com.karakoca.notificationbox.view.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.karakoca.notificationbox.R
import com.karakoca.notificationbox.view.common.NotiDialog

@Composable
fun NotificationDetailScreen(
    viewModel: NotificationDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val removeDialog = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = removeDialog.value) {
            NotiDialog(
                buttonText = android.R.string.ok,
                dismissButtonText = android.R.string.cancel,
                title = stringResource(id = R.string.delete),
                text = stringResource(id = R.string.are_u_sure_delete),
                dismissListener = {
                    removeDialog.value = false
                },
                acceptListener = {
                    removeDialog.value = false
                    viewModel.removeHistory()
                    onBackPressed.invoke()
                }
            )
        }


        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.primary)
                    .padding(16.dp)

            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.clickable {
                        onBackPressed.invoke()
                    },
                    tint = colorScheme.surface
                )

                Text(
                    text = viewModel.notifications.lastOrNull()?.title ?: "",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                    color = colorScheme.surface
                )

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "back",
                    modifier = Modifier.clickable {
                        removeDialog.value = true
                    },
                    tint = colorScheme.surface
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), content = {
                    itemsIndexed(items = viewModel.notifications) { index, value ->
                        if (index == 0)
                            Divider(thickness = 8.dp, color = Color.Transparent)
                        NDItem(item = value)
                        if (index == viewModel.notifications.lastIndex)
                            Divider(thickness = 24.dp, color = Color.Transparent)
                    }
                })
        }
    }
}