package com.settowally.settowally.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.settowally.settowally.presentation.photo_detail.PhotoDetailViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun PhotoDetailScreen(photoId: Int?, viewModel: PhotoDetailViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    if (photoId != null) {
        viewModel.getPhotoById(photoId)
    }
    val hazeState = remember { HazeState() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .haze(state = hazeState),
            contentScale = ContentScale.Fit,
            model = uiState.value.selectedQualityLink,
            contentDescription = uiState.value.photo?.avgColor
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .hazeChild(hazeState),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Button(onClick = { /*TODO*/ }) {
                
            }
            Button(onClick = { /*TODO*/ }) {
                
            }
            Button(onClick = { /*TODO*/ }) {
                
            }
        }
    }
}
