package com.settowally.settowally.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.settowally.settowally.core.theme.LocalSpacing
import com.settowally.settowally.core.util.QualitySelector
import com.settowally.settowally.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Landscape.name) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == QualitySelector.Landscape.name,
                onClick = { viewModel.updateQualitySetting(QualitySelector.Landscape.name) },
                label = { Text(text = stringResource(id = AppText.landscape)) }
            )
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Large.name) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == QualitySelector.Large.name,
                onClick = { viewModel.updateQualitySetting(QualitySelector.Large.name) },
                label = { Text(text = stringResource(id = AppText.large)) }
            )
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Large2x.name) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == QualitySelector.Large2x.name,
                onClick = { viewModel.updateQualitySetting(QualitySelector.Large2x.name) },
                label = { Text(text = stringResource(id = AppText.large2x)) }
            )
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Medium.name) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == QualitySelector.Medium.name,
                onClick = { viewModel.updateQualitySetting(QualitySelector.Medium.name) },
                label = { Text(text = stringResource(id = AppText.medium)) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Original.name) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == QualitySelector.Original.name,
                onClick = { viewModel.updateQualitySetting(QualitySelector.Original.name) },
                label = { Text(text = stringResource(id = AppText.original)) }
            )
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Portrait.name) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == QualitySelector.Portrait.name,
                onClick = { viewModel.updateQualitySetting(QualitySelector.Portrait.name) },
                label = { Text(text = stringResource(id = AppText.portrait)) }
            )
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Small.name) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == QualitySelector.Small.name,
                onClick = { viewModel.updateQualitySetting(QualitySelector.Small.name) },
                label = { Text(text = stringResource(id = AppText.small)) }
            )
            FilterChip(
                leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Tiny.name) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                },
                selected = uiState.value.selectedChip == QualitySelector.Tiny.name,
                onClick = { viewModel.updateQualitySetting(QualitySelector.Tiny.name) },
                label = { Text(text = stringResource(id = AppText.tiny)) }
            )
        }
    }
}