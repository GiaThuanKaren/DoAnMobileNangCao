package com.example.standardblognote.navigation.navhost


import android.content.Context
import android.provider.Settings.Secure.ANDROID_ID
import android.provider.Settings.Secure.getString
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.model.Token
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.network.RetrofitInstance
import com.example.standardblognote.ui.screen.DocumentNote
import com.example.standardblognote.ui.screen.Home
import com.example.standardblognote.ui.screen.HomeScreen
import com.example.standardblognote.ui.screen.LoginScreen
import com.example.standardblognote.ui.screen.Profile.Profile
import com.example.standardblognote.ui.screen.Profile.ProfileDetail
import com.example.standardblognote.ui.screen.SignUpScreen
import com.example.standardblognote.ui.screen.SplashScreen
import com.example.standardblognote.ui.screen.TermsAndConditionsScreen
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException
import androidx.lifecycle.lifecycleScope
@Composable
fun AppNavHost(
    navController: NavHostController,
    context : Context,
    modifier: Modifier,
    homeViewModel: HomeViewModel
) {
    val idDevice = getString(LocalContext.current.contentResolver, ANDROID_ID)
    val coroutineScope1 = rememberCoroutineScope()

    homeViewModel.checkForActiveSession()

    suspend fun UpdateToken() {
        val token = Firebase.messaging.token.await()
        val tokenModel = Token(idDevice, 2, token)

        val res = try {
            RetrofitInstance.api.UpdateToken(tokenModel)
        } catch (e: HttpException) {
            Log.i("INFO Api Call Fail", "${e.message()}")
            return
        } catch (e: IOException) {
            Log.i("INFO Api Call Fail", "${e.message}")
            return
        }

        Log.i("Call api", "${res.body()}")
        if (res.isSuccessful && res.body() != null) {
            val response = res.body()!!
            if (response != null) {
                Log.i("Update", "Update Token Done!!!")
            }
        }
    }

    if (homeViewModel.isUserLoggedIn.value == true) {
        navController.navigate(NavigationItem.Home.route)

        LaunchedEffect(Unit) {
            UpdateToken()
        }
    }

    var isSplashScreenFinished by rememberSaveable {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        startDestination = if (isSplashScreenFinished) {
//            NavigationItem.Login.route
            NavigationItem.Home.route
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
                documentId -> navController.navigate("${NavigationItem.Document.route}/${documentId}/null")
            }, navController, homeViewModel, context)
        }

        composable("${NavigationItem.Document.route}/{documentId}/{parentDocumentId}",
            arguments = listOf(
                navArgument("documentId") {
                    type = NavType.StringType
                },
                navArgument("parentDocumentId") {
                    type = NavType.StringType
                    nullable = true
                }
            )) {
            val documentId = it.arguments?.getString("documentId")
            val parentDocumentId = it.arguments?.getString("parentDocumentId")
            if (parentDocumentId != null) {
                Log.i("ParentDocumentId in Create", parentDocumentId)
            }
            Log.i("ParentDocumentId in Create", "is null")
            documentId?.let { id ->
//                parentDocumentId?.let { parentDocumentId ->
                    DocumentNote(id, parentDocumentId, navController, homeViewModel)
//                }
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
            LoginScreen(context = context, navController)
        }
        composable(NavigationItem.Signup.route) {
            SignUpScreen(navController)
        }
        composable(NavigationItem.TermsAndConditions.route) {
            TermsAndConditionsScreen(navController)
        }
    }

    LaunchedEffect(Navigator.destination.value) {
        when(Navigator.destination.value) {
            is NavigationItem.Home -> {
                navController.navigate(NavigationItem.Home.route)
            }
            is NavigationItem.Login -> {
                navController.navigate(NavigationItem.Login.route)
            }
            else -> {}
        }
    }
}

