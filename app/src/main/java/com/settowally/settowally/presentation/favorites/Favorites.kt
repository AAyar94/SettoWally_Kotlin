package com.settowally.settowally.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.settowally.settowally.core.theme.SettoWallyTheme

@Composable
fun FavoritesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {

    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
fun PreviewFavoriteScreen() {
    SettoWallyTheme {
        FavoritesScreen()
    }
}