package com.karakoca.notificationbox.model.local

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("homeScreen")
    data object OnBoardingScreen : Screen("onboardingScreen")
}