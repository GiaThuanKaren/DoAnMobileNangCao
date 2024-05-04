package com.example.standardblognote.ui.screen

import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import com.example.standardblognote.R
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.model.Recent
import com.example.standardblognote.recents
import com.example.standardblognote.services.NotificationService
import com.example.standardblognote.ui.Components.DocumentListStream
import com.example.standardblognote.ui.Components.Navbar
import com.example.standardblognote.ui.Components.RecentList
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(onDocument: (String) -> Unit = {}, navController: NavController, homeViewModel: HomeViewModel, context: Context) {
    //    lấy use uid
    val uid by homeViewModel.uid.observeAsState()
    val emailId by homeViewModel.emailId.observeAsState()
    val NotificationService = NotificationService(context = context)

    LaunchedEffect(Unit) {
        homeViewModel.fetchCurrentUserUid()
        homeViewModel.getUserData()
    }
    // Lấy UID từ SharedPreferences
    // val uid = homeViewModel.getUidFromSharedPreferences()

    recents = listOf(
        Recent(
            coverImage = R.drawable.imagecover,
            title = "Unofficial Notion Design System v1.1"
        ),
        Recent(
            coverImage = R.drawable.imagecover2,
            title = "Apple Design Resources - iOS 17 and IPadOS 17"
        ),
        Recent(
            coverImage = R.drawable.imagecover3,
            title = "SALY - 3D Illustration Pack"
        ),
        Recent(
            coverImage = R.drawable.imagecover4,
            title = "coolicons | Free Iconset"
        ),
        Recent(
            coverImage = R.drawable.imagecover6,
            title = "Instagram UI Mockup 2.0"
        ),
        Recent(
            coverImage = R.drawable.imagecover7,
            title = "A selection of Google Fonts"
        ),
        Recent(
            coverImage = R.drawable.imagecover8,
            title = "Marvie✨ IOS UI Kit Dark theme"
        ),
        Recent(
            coverImage = R.drawable.imagecover9,
            title = "NLW eSports"
        ),
        Recent(
            coverImage = R.drawable.imagecover10,
            title = "Free Mockups for Dribble Shot"
        ),
    )
//    Log.i("HomeScreen", "HomeScreen is Re-Render")
//    Log.i("Get UID", "${uid}")
    Column {
        Navbar(emailId)
        RecentList(recents = recents)
        DocumentListStream(onDocument, navController) //homeViewModel

        val postNotificationPermission =
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

        LaunchedEffect(key1 = true) {
            if (!postNotificationPermission.status.isGranted) {
                postNotificationPermission.launchPermissionRequest()
            }
        }
        Button(
            onClick = {
                NotificationService.showBasicNotification()
            }
        ) {
            Text(text = "Show basic notification")
        }
    }
}