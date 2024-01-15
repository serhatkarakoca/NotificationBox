package com.karakoca.notificationbox.view.common

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties

@Composable
fun NotiDialog(
    @StringRes buttonText: Int,
    @StringRes dismissButtonText: Int?,
    title: String,
    text: String,
    dismissListener: () -> Unit,
    acceptListener: () -> Unit
) {
    AlertDialog(onDismissRequest = { }, confirmButton = {
        Button(onClick = {
            acceptListener.invoke()
        }) {
            Text(text = stringResource(id = buttonText))
        }
    }, title = { Text(text = title) }, text = { Text(text = text) }, dismissButton = {
        dismissButtonText?.let {
            Button(
                onClick = {
                    dismissListener.invoke()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = stringResource(id = dismissButtonText))
            }
        }
    }, properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false))
}