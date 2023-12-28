package com.example.notificationbox.model.local

import com.example.notificationbox.R

sealed class Screen(val route: String) {
    data object HomeScreen: Screen("homeScreen")
}