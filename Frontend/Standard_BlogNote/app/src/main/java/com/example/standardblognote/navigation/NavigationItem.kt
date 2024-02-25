package com.example.standardblognote.navigation

enum class Screen {
    SPLASH,
    HOME,
    DOCUMENT,
    PROFILE,
    PROFILEDETAIL
}

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Document : NavigationItem(Screen.DOCUMENT.name)

    object Profile : NavigationItem(Screen.PROFILE.name)
    object ProfileDetail : NavigationItem(Screen.PROFILEDETAIL.name)
}