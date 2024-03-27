//
//package com.example.standardblognote.app
//
//
//import android.content.Context
//import androidx.compose.animation.Crossfade
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.standardblognote.data.home.HomeViewModel
//import com.example.standardblognote.navigation.PostOfficeAppRouter
//import com.example.standardblognote.navigation.Screen
//import com.example.standardblognote.ui.screen.HomeScreen
//import com.example.standardblognote.ui.screen.LoginScreen
//import com.example.standardblognote.ui.screen.SignUpScreen
//import com.example.standardblognote.ui.screen.TermsAndConditionsScreen
//
//@Composable
//fun PostOfficeApp(context: Context, homeViewModel: HomeViewModel = viewModel()) {
//
//    homeViewModel.checkForActiveSession()
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color.White
//    ) {
//
//        if (homeViewModel.isUserLoggedIn.value == true) {
//            PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
//        }
//        Crossfade(targetState = PostOfficeAppRouter.currentScreen,label="") { currentState ->
//            when(currentState.value) {
//                is Screen.SignUpScreen -> {
//                    SignUpScreen()
//                }
//
//                is Screen.TermsAndConditionsScreen -> {
//                    TermsAndConditionsScreen()
//                }
//
//                is Screen.LoginScreen -> {
//                    LoginScreen(context = context)
//                }
//
//                is Screen.HomeScreen -> {
//                    HomeScreen()
//
//                }
//                else ->{
//                }
//            }
//        }
//    }
//}
