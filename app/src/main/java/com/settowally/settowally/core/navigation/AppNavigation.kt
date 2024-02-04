package com.settowally.settowally.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.settowally.settowally.presentation.PhotoDetailScreen
import com.settowally.settowally.presentation.favorites.FavoritesScreen
import com.settowally.settowally.presentation.home.HomeScreen
import com.settowally.settowally.presentation.settings.SettingsScreen
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val hazeState = remember { HazeState() }
    Scaffold(modifier = Modifier
        .fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent)
                    .hazeChild(
                        hazeState
                    ),
                navController = navController,
                onItemClick = { navController.navigate(it) },
                color = Color.Transparent
            )
        }) { paddingValues ->
        NavHost(
            modifier = Modifier
                .padding(paddingValues)
                .haze(
                    state = hazeState,
                    style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface)
                ),
            navController = navController,
            startDestination = Route.HOME
        )
        {
            composable(Route.HOME) {
                HomeScreen(
                    onPhotoClick = { photoId ->
                        navController.navigate(Route.PHOTO_DETAIL + "/${photoId}")
                    })
            }
            composable(Route.FAVORITES) {
                FavoritesScreen(onPhotoClick = { photoId ->
                    navController.navigate(Route.PHOTO_DETAIL + "/${photoId}")

                })
            }
            composable(Route.SETTINGS) {
                SettingsScreen()
            }
            composable(
                Route.PHOTO_DETAIL + "/{photoId}",
                arguments = listOf(navArgument("photoId") {
                    type = NavType.IntType
                })
            ) {
                val photoId = it.arguments?.getInt("photoId")
                PhotoDetailScreen(photoId)
            }
        }
    }
}