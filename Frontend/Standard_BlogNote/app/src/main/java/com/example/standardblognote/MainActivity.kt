package com.example.standardblognote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.data.login.LoginViewModel
//import com.example.standardblognote.app.PostOfficeApp
import com.example.standardblognote.model.Recent


import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.navigation.Screen
import com.example.standardblognote.navigation.navhost.AppNavHost

import com.example.standardblognote.ui.Components.*
import com.example.standardblognote.ui.screen.DocumentNote
import com.example.standardblognote.ui.theme.StandardBlogNoteTheme
import com.example.standardblognote.ui.utils.Constants.MY_USER_ID
import kotlinx.coroutines.flow.observeOn

var recents: List<Recent> = emptyList()

class MainActivity : ComponentActivity() {
    val homeViewModel: HomeViewModel by viewModels()

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
                                            if (it.route == NavigationItem.Profile.route) {
                                                navController.navigate(
                                                    "${NavigationItem.Profile.route}/$MY_USER_ID"
                                                )
                                            } else {
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

