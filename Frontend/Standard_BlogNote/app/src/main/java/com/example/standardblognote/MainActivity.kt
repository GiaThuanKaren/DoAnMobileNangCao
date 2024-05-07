package com.example.standardblognote


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.data.login.LoginViewModel
import com.example.standardblognote.data.signup.SignupViewModel
import com.example.standardblognote.model.ChatViewModel
import com.example.standardblognote.model.DocumentModel
import com.example.standardblognote.model.Recent
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Screen
import com.example.standardblognote.navigation.navhost.AppNavHost
import com.example.standardblognote.network.RetrofitInstance
import com.example.standardblognote.ui.Components.BottomNavItem
import com.example.standardblognote.ui.Components.BottomNavigationBar
import com.example.standardblognote.ui.theme.StandardBlogNoteTheme
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


var recents: List<Recent> = emptyList()

class MainActivity : ComponentActivity() {
    val homeViewModel: HomeViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()
    val signupViewModel: SignupViewModel by viewModels()

    private val viewModel :ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uid = homeViewModel.getUidFromSharedPreferences() ?: ""
        homeViewModel.fetchUidLogin()
        homeViewModel.uidShared.observe(this, Observer { id ->
            if (id != null) {
                uid = id
            }
        })
        requestNoftiPermission()

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
                                                NavigationItem.Profile.route,
                                                Screen.PROFILE.name,
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
                                                    val document = DocumentModel("Untitled", "", "", "", null, uid)
                                                    val res = try {
                                                        RetrofitInstance.api.CreateNewDocument(document)
                                                    } catch (e: HttpException) {
                                                        Log.i("INFO Api Call Fail", "${e.message()}")
                                                        return@launch
                                                    } catch (e: IOException) {
                                                        Log.i("INFO Api Call Fail", "${e.message}")
                                                        return@launch
                                                    }

                                                    Log.i("Call api", "${res.body()}")
                                                    if (res.isSuccessful && res.body() != null) {
                                                        val response = res.body()!!
                                                        if (response != null) {
                                                            navController.navigate("${NavigationItem.Document.route}/${response.data.post_id}/null")
                                                        }
                                                    }
                                                }
                                            } else if (it.route == NavigationItem.Profile.route) {
                                                navController.navigate(NavigationItem.Profile.route)
                                            }
                                            else {
//                                                navController.navigate(it.route)
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
                            AppNavHost(navController,this@MainActivity, modifier = Modifier, homeViewModel)
                        }
                    }
                }
            }
        }
    }

    private fun requestNoftiPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}