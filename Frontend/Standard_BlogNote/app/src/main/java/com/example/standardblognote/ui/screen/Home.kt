package com.example.standardblognote.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.standardblognote.R
import com.example.standardblognote.model.Recent
import com.example.standardblognote.recents
import com.example.standardblognote.ui.Components.DocumentListStream
import com.example.standardblognote.ui.Components.Navbar
import com.example.standardblognote.ui.Components.RecentList

@Composable
fun Home(onDocument: (String) -> Unit = {}, navController: NavController) {
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
    Column {
        Navbar()
        RecentList(recents = recents)
        DocumentListStream(onDocument, navController)
    }
}