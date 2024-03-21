package com.example.standardblognote.navigation.navhost


import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.ui.screen.DocumentNote
import com.example.standardblognote.ui.screen.Home
import com.example.standardblognote.ui.screen.LoginScreen
import com.example.standardblognote.ui.screen.Profile.Profile
import com.example.standardblognote.ui.screen.Profile.ProfileDetail
import com.example.standardblognote.ui.screen.SignUpScreen
import com.example.standardblognote.ui.screen.SpashScreen
import com.example.standardblognote.ui.screen.TermsAndConditionsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    context : Context,
    modifier: Modifier,
    homeViewModel: HomeViewModel
) {
    var isSplashScreenFinished by rememberSaveable {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        startDestination = if (isSplashScreenFinished) {
            NavigationItem.Login.route
        } else {
            NavigationItem.Splash.route
        }
    ) {
        composable(NavigationItem.Splash.route) {
            SpashScreen {
                navController.navigate(NavigationItem.Home.route)
                isSplashScreenFinished = true
            }
        }
        composable(NavigationItem.Home.route) {
            Home(onDocument = {
                documentId -> navController.navigate("document/${documentId}")
            }, navController, homeViewModel)
        }

        composable("${NavigationItem.Document.route}/{documentId}",
            arguments = listOf(
                navArgument("documentId") {
                    type = NavType.StringType
                }
            )) {
            val documentId = it.arguments?.getString("documentId")
            documentId?.let { id ->
                DocumentNote(id, navController, homeViewModel)
            }
        }
        //rote Profile
        composable(NavigationItem.Profile.route) {
            Profile(
                openProfileDetail = {navController.navigate(NavigationItem.ProfileDetail.route)},
                navController,
                homeViewModel
            )
        }

        composable(NavigationItem.ProfileDetail.route) {
            ProfileDetail (
                navController,
                homeViewModel
            )
        }

        composable(NavigationItem.Login.route) {
//            LoginScreen(navController)
            LoginScreen(context = context,navController)
        }
        composable(NavigationItem.Signup.route) {
            SignUpScreen(navController)
        }
        composable(NavigationItem.TermsAndConditions.route) {
            TermsAndConditionsScreen(navController)
        }
    }
}

