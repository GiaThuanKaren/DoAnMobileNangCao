package com.example.standardblognote


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.data.login.LoginViewModel
import com.example.standardblognote.data.signup.SignupViewModel
import com.example.standardblognote.model.DocumentModel
import com.example.standardblognote.model.Recent
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.navigation.Screen
import com.example.standardblognote.navigation.navhost.AppNavHost
import com.example.standardblognote.network.RetrofitInstance
import com.example.standardblognote.ui.Components.*

import com.example.standardblognote.ui.screen.DocumentNote

import com.example.standardblognote.ui.theme.StandardBlogNoteTheme
import retrofit2.HttpException
import java.io.IOException
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

var recents: List<Recent> = emptyList()

class MainActivity : ComponentActivity() {
    val homeViewModel: HomeViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()
    val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StandardBlogNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val screens = listOf(
                        NavigationItem.Home.route,
                        NavigationItem.Search.route,
                        NavigationItem.Notification.route,
                        "${NavigationItem.Document.route}/{documentId}",
                    )
                    val showBottomBar = navController
                        .currentBackStackEntryAsState().value?.destination?.route in screens.map { it }
                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(
                                visible = showBottomBar,
                                enter = fadeIn() + scaleIn(),
                                exit = fadeOut() + scaleOut(),
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier
                                        .background(MaterialTheme.colors.background)
                                        .fillMaxWidth()
                                ) {
                                    BottomNavigationBar(
                                        items = listOf(
                                            BottomNavItem(
                                                NavigationItem.Home.route,
                                                Screen.HOME.name,
                                                icon = R.drawable.list
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Search.route,
                                                Screen.SEARCH.name,
                                                icon = R.drawable.search
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Notification.route,
                                                Screen.NOTIFICATION.name,
                                                icon = R.drawable.bell
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Document.route,
                                                Screen.DOCUMENT.name,
                                                icon = R.drawable.plus
                                            ),
                                        ),
                                        navController = navController,
                                        onItemClick = {
//                                            if (it.route == NavigationItem.Profile.route) {
//                                                navController.navigate(
//                                                    "${NavigationItem.Profile.route}/$MY_USER_ID"
//                                                )
//                                            }
                                            if (it.route == NavigationItem.Document.route) {
                                                lifecycleScope.launch {
                                                    val document = DocumentModel("Untitled", "", "", "", null, 0)
                                                    val res = try {
                                                        RetrofitInstance.api.CreateNewDocument(document)
                                                    } catch (e: HttpException) {
                                                        Log.i("INFO Api Call Fail", "${e.message()}")
                                                    } catch (e: IOException) {
                                                        Log.i("INFO Api Call Fail", "${e.message}")
                                                    }

                                                    Log.i("Call api", "${res}")
                                                    //        navController.navigate("document/${id}")
                                                    //        if (res.isSuccessful && res.body() != null) {
                                                    //                val response = res.body()!!
                                                    //                            && response.msg == 200
                                                    //                    if (response != null) {
                                                    //                        apiDocuments = response.data!!
                                                    //                        Log.i("STANDARDs", "${apiDocuments}")
                                                    //                    }
                                                    //        }
                                                }
                                            }
                                            else {
                                                navController.navigate(it.route)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues = innerPadding)
                        ) {
                            MyNotionApp(navController,this@MainActivity, modifier = Modifier, homeViewModel)

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MyNotionApp(navController: NavHostController, context: Context, modifier: Modifier, homeViewModel: HomeViewModel) {
//    val destination by navigator.destination.collectAsState()
    Log.i("NavController a", "${Navigator.destination}")

    when(Navigator.destination.value) {
        is NavigationItem.Home -> {
            navController.navigate(NavigationItem.Home.route)
        }
    }
//    LaunchedEffect(destination) {
//        if (navController.currentDestination?.route != destination.route) {
//            navController.navigate(destination.route)
//        }
//    }
    AppNavHost(navController,context, modifier, homeViewModel)
}

