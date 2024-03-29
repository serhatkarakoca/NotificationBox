package com.karakoca.notificationbox.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karakoca.notificationbox.model.local.Screen
import com.karakoca.notificationbox.view.home.HomeScreen
import com.karakoca.notificationbox.view.onboarding.OnBoardingScreen

@Composable
fun MainNavController() {

    val navController = rememberNavController()

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen()
            }

            composable(route = Screen.OnBoardingScreen.route) {
                OnBoardingScreen()
            }
        }
    }


}