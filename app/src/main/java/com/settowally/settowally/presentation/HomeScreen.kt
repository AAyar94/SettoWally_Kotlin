package com.settowally.settowally.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.settowally.settowally.R
import com.settowally.settowally.core.theme.SettoWallyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            SearchBar(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                query = "",
                placeholder = { Text(text = "Search") },
                windowInsets = WindowInsets.ime,
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {}) {
            }
        }

    }
}

@PreviewLightDark
@PreviewDynamicColors()
@Composable
fun PreviewHomeScreen() {
    SettoWallyTheme {
        HomeScreen()
    }
}