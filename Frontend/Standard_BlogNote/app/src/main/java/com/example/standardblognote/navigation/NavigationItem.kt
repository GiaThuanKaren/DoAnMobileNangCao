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
    
interface NavigationDestination {
    val route: String
}
sealed class NavigationItem(override val route: String): NavigationDestination {
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
//    val destination: MutableStateFlow<NavigationDestination> = MutableStateFlow(NavigationItem.Login)
var destination: MutableState<NavigationDestination> = mutableStateOf(NavigationItem.Login)
    fun navigate(destination: NavigationDestination) {
        this.destination.value = destination
        Log.i("Destination 1", "${destination.route}")
    }
}

//sealed class Screen {
//    object Home : Screen()
//    object Splash : Screen()
//    object Search : Screen()
//    object Profile : Screen()
//    object Document : Screen()
//    object Notification : Screen()
//    object ProfileDetail : Screen()
//}
//
//sealed class NavigationItem(val route: String) {
//    object Splash : NavigationItem(Screen.Splash.javaClass.simpleName)
//    object Home : NavigationItem(Screen.Home.javaClass.simpleName)
//    object Document : NavigationItem(Screen.Document.javaClass.simpleName)
//    object Profile : NavigationItem(Screen.Profile.javaClass.simpleName)
//    object ProfileDetail : NavigationItem(Screen.ProfileDetail.javaClass.simpleName)
//    object Search : NavigationItem(Screen.Search.javaClass.simpleName)
//    object Notification : NavigationItem(Screen.Notification.javaClass.simpleName)
//}
//
