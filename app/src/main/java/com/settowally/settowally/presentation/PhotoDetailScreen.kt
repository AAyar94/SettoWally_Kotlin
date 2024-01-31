package com.settowally.settowally.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes

@Composable
fun PhotoDetailScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
fun PreviewPhotoDetail() {
    PhotoDetailScreen()
}