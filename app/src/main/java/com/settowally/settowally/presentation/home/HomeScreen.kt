package com.settowally.settowally.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.settowally.settowally.R
import com.settowally.settowally.core.theme.LocalSpacing
import com.settowally.settowally.core.theme.SettoWallyTheme
import com.settowally.settowally.core.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onPhotoClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    val uiState = viewModel.uiState.collectAsState()
    val page = remember {
        1
    }
    val lazyGridState = rememberLazyGridState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onPhotoClick()
                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .clickable { viewModel.getPhotos(page) },
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            SearchBar(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                query = uiState.value.searchQuery ?: "",
                placeholder = { Text(text = uiState.value.searchPlaceHolder ?: "") },
                windowInsets = WindowInsets.ime,
                onQueryChange = viewModel::searchQueryUpdate,
                onSearch = viewModel::onSearch,
                active = uiState.value.isSearchFocused,
                onActiveChange = viewModel::searchFocusChange
            ) {
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            state = lazyGridState,
            verticalArrangement = Arrangement.SpaceAround,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(uiState.value.photosList?.toList() ?: emptyList()) {
                PhotoItem(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(spacing.spaceMedium),
                    homePhotoDataModel = it
                )
            }
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
fun PreviewHomeScreen() {
    SettoWallyTheme {
        HomeScreen(onPhotoClick = {})
    }
}