package com.example.standardblognote

//import com.example.standardblognote.app.PostOfficeApp


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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.model.Recent
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.navigation.Screen
import com.example.standardblognote.navigation.navhost.AppNavHost
import com.example.standardblognote.ui.Components.BottomNavItem
import com.example.standardblognote.ui.Components.BottomNavigationBar
import com.example.standardblognote.ui.theme.StandardBlogNoteTheme
import com.example.standardblognote.ui.utils.Constants.MY_USER_ID

var recents: List<Recent> = emptyList()

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

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
                            MyNotionApp(navController, modifier = Modifier, homeViewModel)
                            //PostOfficeApp(this@MainActivity)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MyNotionApp(navController: NavHostController, modifier: Modifier, homeViewModel: HomeViewModel) {
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
    AppNavHost(navController, modifier, homeViewModel)
}

