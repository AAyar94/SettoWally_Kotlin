package com.settowally.settowally.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomBarItems(
    val route: String,
    val icon: ImageVector,
    val label: String
)

private val bottomBarItems = listOf(
    BottomBarItems(Route.FAVORITES, Icons.Default.Favorite, "favorites"),
    BottomBarItems(Route.HOME, Icons.Default.Home, "Home"),
    BottomBarItems(
        Route.SETTINGS, Icons.Default.Settings, "Settings"
    ),
)

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    items: List<BottomBarItems> = bottomBarItems,
    onItemClick: (String) -> Unit,
    color: Color
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier,
        containerColor = color,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                selected = item.route == currentRoute,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = { onItemClick(item.route) },
            )
        }
    }
}