//package com.example.standardblognote.navigation
//
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//
//sealed class Screens {
//
//    object SignUpScreen : Screens()
//    object TermsAndConditionsScreen : Screens()
//    object LoginScreen : Screens()
//    object Home : Screens()
//}
//
//
//object PostOfficeAppRouter {
//
//    var currentScreen: MutableState<Screens> = mutableStateOf(Screens.SignUpScreen)
//
//    fun navigateTo(destination : Screens){
//        currentScreen.value = destination
//    }
//}