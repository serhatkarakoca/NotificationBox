package com.karakoca.notificationbox.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.karakoca.notificationbox.R
import com.karakoca.notificationbox.model.NotificationUI
import com.karakoca.notificationbox.model.local.Constants.TODAY
import com.karakoca.notificationbox.model.local.Constants.YESTERDAY
import com.karakoca.notificationbox.util.getDateString


@Composable
fun NotificationItem(item: NotificationUI) {
    var date = remember {
        getDateString(item.`when`)
    }

    val color = Color(item.color)
    val expanded = remember {
        mutableStateOf(item.expanded)
    }
    var imageExpanded by remember {
        mutableStateOf(false)
    }

    date = when (date) {
        TODAY -> stringResource(id = R.string.today)
        YESTERDAY -> stringResource(id = R.string.yesterday)
        else -> date.substringBefore(" ")
    }

    Column(Modifier.padding(bottom = 16.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable { expanded.value = !expanded.value },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(10.dp)
                        .background(color)
                )
                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .border(2.dp, color, CircleShape)
                            .clip(CircleShape)
                            .background(Color.Transparent)
                    ) {
                        Text(
                            text = "2",
                            Modifier.align(Alignment.Center),
                            color = color,
                            fontSize = 24.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = date)
                    Text(
                        text = item.date?.substringAfter(" ") ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Divider(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                Column(modifier = Modifier.padding(8.dp)) {
                    Row {
                        item.icon?.asImageBitmap()?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "icon",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clip(
                                        CircleShape
                                    )
                            )
                        }

                        Text(
                            text = item.title ?: "",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = color
                        )
                    }

                    Text(
                        text = item.text ?: "",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 24.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
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
                        .align(Alignment.CenterHorizontally)
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

        AnimatedVisibility(visible = expanded.value, modifier = Modifier.padding(top = 8.dp)) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFAFAFA))
                    .padding(8.dp)

            ) {
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
        }
    }

}