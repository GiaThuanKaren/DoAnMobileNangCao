package com.example.standardblognote.navigation

import android.util.Log
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

}
<<<<<<< HEAD
    
interface NavigationDestination {
    val route: String
}
sealed class NavigationItem(override val route: String): NavigationDestination {
=======

sealed class NavigationItem(val route: String) {
>>>>>>> 8a10fbedf4271859838c55c89e5f6d92fcc504da
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Home : NavigationItem(Screen.HOME.name)
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

