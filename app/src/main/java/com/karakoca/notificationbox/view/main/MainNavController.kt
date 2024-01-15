package com.karakoca.notificationbox.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.karakoca.notificationbox.data.model.Constants.NOTIFICATION_ITEM
import com.karakoca.notificationbox.data.model.NotificationUI
import com.karakoca.notificationbox.data.model.Screen
import com.karakoca.notificationbox.view.details.NotificationDetailScreen
import com.karakoca.notificationbox.view.home.HomeScreen
import com.karakoca.notificationbox.view.onboarding.OnBoardingScreen

@Composable
fun MainNavController() {

    val navController = rememberNavController()
    val gson = Gson()

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(
                    navigateToDetails = {
                        val jsonString =
                            gson.toJson(it, object : TypeToken<List<NotificationUI>>() {}.type)
                        navController.navigate(Screen.NotificationDetails.route + "?$NOTIFICATION_ITEM=$jsonString")
                    }
                )
            }

            composable(route = Screen.OnBoardingScreen.route) {
                OnBoardingScreen()
            }

            composable(route = Screen.NotificationDetails.route + "?$NOTIFICATION_ITEM={$NOTIFICATION_ITEM}",
                arguments = listOf(
                    navArgument(NOTIFICATION_ITEM) {
                        type = NavType.StringType
                        defaultValue = null
                        nullable = true
                    }
                )) {
                NotificationDetailScreen(onBackPressed = {
                    navController.popBackStack()
                })
            }
        }
    }


}