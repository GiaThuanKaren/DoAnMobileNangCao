package com.example.standardblognote.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


enum class Screen {
    HOME,
    LOGIN,
    SIGNUP,
    SPLASH,
    SEARCH,
    PROFILE,
    DOCUMENT,
    NOTIFICATION,
    PROFILEDETAIL,
    TERMSANDCONDITIONS,
    ChangePasword

}

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Home : NavigationItem(Screen.HOME.name)
    object ChangePasword : NavigationItem(Screen.ChangePasword.name)

    object Document : NavigationItem(Screen.DOCUMENT.name)
    object Profile : NavigationItem(Screen.PROFILE.name)
    object ProfileDetail : NavigationItem(Screen.PROFILEDETAIL.name)
    object Search : NavigationItem(Screen.SEARCH.name)
    object Notification : NavigationItem(Screen.NOTIFICATION.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object Signup : NavigationItem(Screen.SIGNUP.name)
    object TermsAndConditions : NavigationItem(Screen.TERMSANDCONDITIONS.name)
}

object Navigator {
    var destination: MutableState<NavigationItem> = mutableStateOf(NavigationItem.Login)
    fun navigate(destination: NavigationItem) {
        this.destination.value = destination
    }
}