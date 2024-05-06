package com.example.standardblognote.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


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

sealed class NavigationItem(val route: String) {
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

