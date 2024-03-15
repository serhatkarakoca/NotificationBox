package com.karakoca.notificationbox.view.onboarding

import android.content.Intent
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.karakoca.notificationbox.R
import com.karakoca.notificationbox.data.model.Constants
import com.karakoca.notificationbox.data.model.OnBoardingUI
import com.karakoca.notificationbox.util.AutoStartSetting
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(navigateToHome: () -> Unit) {
    val context = LocalContext.current

    val pagerList = listOf<OnBoardingUI>(
        OnBoardingUI(
            "Hiçbir Bildirim Kaybolmaz",
            "Bildirimlerinizi kaydetmenin en kolay yolu. Kaçırdığınız hiçbir şey yok.",
            R.drawable.onb
        ),
        OnBoardingUI(
            "Bildirim Geçmişi",
            "Gelen bildirimleri filtreleyebilir, istemediğin uygulamalardan bildirim gelmesini engelleyebilirsin.",
            R.drawable.ic_launcher_background
        ),
        OnBoardingUI(
            "Son Bir Adım",
            "Uygulamayı Kullanabilmek İçin Bildirim İzinlerini Vermen Gerekiyor",
            R.drawable.ic_launcher_background
        )
    )

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = {
        pagerList.size
    })

    val manufacturer = Constants.brandList.firstOrNull {
        it.lowercase().equals(Build.MANUFACTURER, ignoreCase = true)
    }

    var autoStartStatus by remember {
        mutableStateOf(manufacturer == null)
    }

    val notificationPermStatus =
        NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.packageName)

    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(state = pagerState) { page ->

            // Card content
            Box(
                Modifier
                    .fillMaxSize()
            ) {
                val item = pagerList[page]
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.67f)
                        .blur(10.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.title,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                    )
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = item.text,
                        textAlign = TextAlign.Center,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                    if (page == pagerList.lastIndex) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp, top = 32.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.enable_notification_permission),
                                Modifier.clickable {
                                    val intent =
                                        Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                                    ContextCompat.startActivity(context, intent, null)
                                })

                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "check_icon",
                                tint = if (notificationPermStatus) Color.Green else Color.LightGray
                            )
                        }

                        if (manufacturer != null)
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.enable_auto_start),
                                    Modifier.clickable {
                                        autoStartStatus = true
                                        AutoStartSetting.startAutoStartDialog(context, manufacturer)
                                    })

                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "check_icon",
                                    tint = if (autoStartStatus) Color.Green else Color.LightGray
                                )
                            }

                    }
                    Button(
                        onClick = {
                            if (page == pagerList.lastIndex)
                                navigateToHome.invoke()
                            else {
                                scope.launch {
                                    pagerState.animateScrollToPage(page + 1)
                                }
                            }

                        },
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                        enabled = (autoStartStatus && notificationPermStatus) || page != pagerList.lastIndex
                    ) {
                        Text(
                            text = if (page != pagerList.lastIndex) stringResource(R.string.next) else stringResource(
                                R.string.done
                            )
                        )
                    }

                }
            }
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}