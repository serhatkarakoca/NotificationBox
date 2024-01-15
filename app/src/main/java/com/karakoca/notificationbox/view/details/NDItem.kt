package com.karakoca.notificationbox.view.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.karakoca.notificationbox.R
import com.karakoca.notificationbox.data.model.Constants
import com.karakoca.notificationbox.data.model.NotificationUI
import com.karakoca.notificationbox.util.TriangleEdgeShape
import com.karakoca.notificationbox.util.getDateString

@Composable
fun NDItem(item: NotificationUI) {
    val interactionSource = remember { MutableInteractionSource() }

    var imageExpanded by remember {
        mutableStateOf(false)
    }

    var expandedMessage by remember {
        mutableStateOf(false)
    }

    if (imageExpanded)
        Dialog(
            onDismissRequest = { imageExpanded = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.black_60))
            ) {
                item.messageImage?.asImageBitmap()?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier
                            .sizeIn(100.dp)
                            .clip(RectangleShape)
                            .align(Alignment.Center),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }

    Column(
        Modifier
            .fillMaxWidth()
            .clickable(interactionSource = interactionSource, indication = null) {
                expandedMessage = !expandedMessage
            }) {

        if (item.isOnlyDate) {
            val date = remember {
                getDateString(item.`when`)
            }

            val dateString = when (date) {
                Constants.TODAY -> stringResource(id = R.string.today)
                Constants.YESTERDAY -> stringResource(id = R.string.yesterday)
                else -> item.date?.substringBefore(" ")
            }

            Row(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        ), color = colorScheme.secondary
                    )
                    .padding(8.dp)
            ) {
                Text(text = dateString.toString(), color = colorScheme.surface, fontSize = 11.sp)
            }

        } else {
            Row(
                Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = colorScheme.primary,
                            shape = RoundedCornerShape(4.dp, 4.dp, 0.dp, 4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {

                    Text(
                        text = item.text ?: "",
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                        color = colorScheme.surface,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )

                    item.messageImage?.asImageBitmap()?.let {
                        Image(
                            bitmap = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RectangleShape)
                                .clickable { imageExpanded = true }
                        )
                    }


                }
                Column(
                    modifier = Modifier
                        .background(
                            color = colorScheme.primary,
                            shape = TriangleEdgeShape(10)
                        )
                        .wrapContentWidth()
                        .fillMaxHeight()
                ) {
                }

            }

            AnimatedVisibility(
                visible = expandedMessage,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(Alignment.End),
            ) {
                Text(
                    text = item.date?.substringAfter(" ") ?: "",
                    color = colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp,
                )
            }
        }


    }
}