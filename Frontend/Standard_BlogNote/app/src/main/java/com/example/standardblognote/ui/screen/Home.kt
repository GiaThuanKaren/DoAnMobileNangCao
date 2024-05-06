package com.example.standardblognote.ui.screen


import android.Manifest
import android.content.Context

import android.provider.Settings

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text

import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.core.content.ContentProviderCompat.requireContext

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import androidx.navigation.NavHostController
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

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(onDocument: (String) -> Unit = {}, navController: NavHostController, homeViewModel: HomeViewModel, context: Context) {
    //    lấy use uid
//    val uid by homeViewModel.uid.observeAsState()
    val emailId by homeViewModel.emailId.observeAsState()
//    val NotificationService = NotificationService(context = context)

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    // Lấy UID từ SharedPreferences
     val uid = homeViewModel.getUidFromSharedPreferences() ?: ""

//    if(uid == "") {
//        LaunchedEffect(Unit) {
//            homeViewModel.fetchCurrentUserUid()
//            homeViewModel.getUserData()
//        }
//    }

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
    Log.i("Get UID", "${uid}")
    Column {
        Navbar(emailId, homeViewModel)
        RecentList(recents = recents)
        DocumentListStream(onDocument, uid, navController) //homeViewModel

        val postNotificationPermission =
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

        LaunchedEffect(key1 = true) {
            if (!postNotificationPermission.status.isGranted) {
                postNotificationPermission.launchPermissionRequest()
            }
        }
    }

//    Button(onClick = {
//
//        scope.launch {
//
//            val token = Firebase.messaging.token.await()
//            Log.i("Id Device",Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID))
//            Log.i("HomeScreen Log Firebase Token", token
//                )
//        }
//
//    }) {
//        Text(text = "Click Here ")
//    }
}