package com.karakoca.notificationbox.data.model

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("homeScreen")
    data object OnBoardingScreen : Screen("onboardingScreen")
    data object NotificationDetails : Screen("notificationDetailScreen")
}