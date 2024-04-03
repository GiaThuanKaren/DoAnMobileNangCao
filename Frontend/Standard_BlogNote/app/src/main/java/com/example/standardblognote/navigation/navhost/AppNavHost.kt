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
import com.example.standardblognote.data.login.LoginViewModel
import com.example.standardblognote.data.signup.SignupViewModel
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.ui.screen.DocumentNote
import com.example.standardblognote.ui.screen.Home
import com.example.standardblognote.ui.screen.LoginScreen
import com.example.standardblognote.ui.screen.Profile.Profile
import com.example.standardblognote.ui.screen.Profile.ProfileDetail
import com.example.standardblognote.ui.screen.SignUpScreen
import com.example.standardblognote.ui.screen.SplashScreen
import com.example.standardblognote.ui.screen.TermsAndConditionsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    context: Context,
    homeViewModel: HomeViewModel,
    loginViewModel: LoginViewModel,
    signupViewModel: SignupViewModel
) {
    var isSplashScreenFinished by rememberSaveable {
        mutableStateOf(false)
    }

    Log.i("App Nav Host", "App Nav Host is Re-Render")

    NavHost(
        navController = navController,
        startDestination = if (isSplashScreenFinished) {
            NavigationItem.Login.route
        } else {
            NavigationItem.Splash.route
        }
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen {
                navController.navigate(NavigationItem.Home.route)
                isSplashScreenFinished = true
            }
        }
        composable(NavigationItem.Home.route) {
            Home(onDocument = {
                documentId -> navController.navigate("document/${documentId}/null")
            }, navController, homeViewModel, context)
        }

        composable("${NavigationItem.Document.route}/{documentId}/{parentDocumentId}",
            arguments = listOf(
                navArgument("documentId") {
                    type = NavType.StringType
                },
                navArgument("parentDocumentId") {
                    type = NavType.StringType
                }
            )) {
            val documentId = it.arguments?.getString("documentId")
            val parentDocumentId = it.arguments?.getString("parentDocumentId")
            documentId?.let { id ->
                parentDocumentId?.let { parentDocumentId ->
                    DocumentNote(id, parentDocumentId, navController, homeViewModel)
                }
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
            LoginScreen(navController, context, loginViewModel, homeViewModel)
        }
        composable(NavigationItem.Signup.route) {
            SignUpScreen(navController, signupViewModel)
        }
        composable(NavigationItem.TermsAndConditions.route) {
            TermsAndConditionsScreen(navController)
        }
    }
}

