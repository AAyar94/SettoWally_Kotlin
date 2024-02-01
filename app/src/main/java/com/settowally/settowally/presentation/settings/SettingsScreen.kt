package com.settowally.settowally.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row {
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == "landscape") {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == "landscape",
                onClick = { viewModel.updateQualitySetting("landscape") },
                label = { Text(text = "Landscape") }
            )
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == "large") {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == "large",
                onClick = { viewModel.updateQualitySetting("large") },
                label = { Text(text = "Large") }
            )
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == "large2x") {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == "large2x",
                onClick = { viewModel.updateQualitySetting("large2x") },
                label = { Text(text = "Large2x") }
            )
        }
    }
}