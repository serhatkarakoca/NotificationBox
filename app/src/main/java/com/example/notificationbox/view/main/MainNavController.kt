package com.example.notificationbox.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notificationbox.model.local.Screen
import com.example.notificationbox.view.home.HomeScreen

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
        }
    }


}