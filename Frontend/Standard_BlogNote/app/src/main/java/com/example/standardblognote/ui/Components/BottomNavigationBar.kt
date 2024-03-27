package com.example.standardblognote.ui.Components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.standardblognote.R

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Int,
    val badgeCount: Int = 0,
)

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        val inactiveColor = Color.Gray
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                modifier = modifier.animateContentSize(
                    animationSpec =  tween(5000)
                ),
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = MaterialTheme.colors.onBackground,
                unselectedContentColor = inactiveColor,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Image(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.name,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            Image(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.name,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                })
        }
    }
}