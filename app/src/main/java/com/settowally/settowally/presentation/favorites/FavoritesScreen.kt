package com.settowally.settowally.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.settowally.settowally.presentation.home.PhotoItem

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(), onPhotoClick: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.value.list.isNullOrEmpty()) {
            FavoriteListEmpty(modifier = Modifier.fillMaxSize())
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
                items(uiState.value.list!!) { photoItem ->
                    PhotoItem(gridPhotoDataModel = photoItem, onPhotoClick = {
                        onPhotoClick(it)
                    })
                }
            }
        }
    }
}
