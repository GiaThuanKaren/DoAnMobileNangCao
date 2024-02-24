package com.example.standardblognote.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.ui.screen.DocumentNote
import com.example.standardblognote.ui.screen.Home

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ) {
        composable(NavigationItem.Home.route) {
            Home(onDocument = {
                documentId -> navController.navigate("document/${documentId}")
            }, navController)
        }
         composable("${NavigationItem.Document.route}/{documentId}",
            arguments = listOf(
                navArgument("documentId") {
                    type = NavType.StringType
                }
            )) {
            val documentId = it.arguments?.getString("documentId")
            documentId?.let { id ->
                DocumentNote(id, navController)
            }
        }
    }
}