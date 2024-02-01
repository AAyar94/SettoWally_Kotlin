package com.settowally.settowally.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.settowally.settowally.presentation.PhotoDetailScreen
import com.settowally.settowally.presentation.favorites.FavoritesScreen
import com.settowally.settowally.presentation.home.HomeScreen
import com.settowally.settowally.presentation.settings.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        BottomNavigationBar(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            navController = navController,
            onItemClick = { navController.navigate(it) })
    }) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Route.HOME
        ) {
            composable(Route.HOME) {
                HomeScreen(onPhotoClick = {})
            }
            composable(Route.FAVORITES) {
                FavoritesScreen()
            }
            composable(Route.SETTINGS) {
                SettingsScreen()
            }
            composable(Route.PHOTO_DETAIL) {
                PhotoDetailScreen()
            }
        }
    }
}