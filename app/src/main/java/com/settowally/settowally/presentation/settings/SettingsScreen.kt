package com.settowally.settowally.presentation.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.settowally.settowally.R
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
    val clearCacheVisibility = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = clearCacheVisibility.value) {
            BasicAlertDialog(
                onDismissRequest = { clearCacheVisibility.value = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.large)
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clip(MaterialTheme.shapes.large)
                        .padding(spacing.spaceMedium),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(id = R.string.are_u_sure_title),
                        textAlign = TextAlign.Center
                    )
                    Spacer(
                        modifier = Modifier.height(spacing.spaceSmall)
                    )
                    Text(
                        stringResource(id = R.string.are_u_sure_message),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    FilledTonalButton(
                        onClick = {
                            viewModel.onClearCache()
                            clearCacheVisibility.value = false
                        },
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.im_sure_delete_all),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Text(
                text = stringResource(id = R.string.quality),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Landscape.name) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = null
                        )
                    }
                },
                    selected = uiState.value.selectedChip == QualitySelector.Landscape.name,
                    onClick = { viewModel.updateQualitySetting(QualitySelector.Landscape.name) },
                    label = { Text(text = stringResource(id = AppText.landscape)) })
                FilterChip(leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Large.name) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = null
                        )
                    }
                },
                    selected = uiState.value.selectedChip == QualitySelector.Large.name,
                    onClick = { viewModel.updateQualitySetting(QualitySelector.Large.name) },
                    label = { Text(text = stringResource(id = AppText.large)) })
                FilterChip(leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Large2x.name) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = null
                        )
                    }
                },
                    selected = uiState.value.selectedChip == QualitySelector.Large2x.name,
                    onClick = { viewModel.updateQualitySetting(QualitySelector.Large2x.name) },
                    label = { Text(text = stringResource(id = AppText.large2x)) })
                FilterChip(leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Medium.name) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = null
                        )
                    }
                },
                    selected = uiState.value.selectedChip == QualitySelector.Medium.name,
                    onClick = { viewModel.updateQualitySetting(QualitySelector.Medium.name) },
                    label = { Text(text = stringResource(id = AppText.medium)) })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Original.name) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = null
                        )
                    }
                },
                    selected = uiState.value.selectedChip == QualitySelector.Original.name,
                    onClick = { viewModel.updateQualitySetting(QualitySelector.Original.name) },
                    label = { Text(text = stringResource(id = AppText.original)) })
                FilterChip(leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Portrait.name) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = null
                        )
                    }
                },
                    selected = uiState.value.selectedChip == QualitySelector.Portrait.name,
                    onClick = { viewModel.updateQualitySetting(QualitySelector.Portrait.name) },
                    label = { Text(text = stringResource(id = AppText.portrait)) })
                FilterChip(leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Small.name) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = null
                        )
                    }
                },
                    selected = uiState.value.selectedChip == QualitySelector.Small.name,
                    onClick = { viewModel.updateQualitySetting(QualitySelector.Small.name) },
                    label = { Text(text = stringResource(id = AppText.small)) })
                FilterChip(leadingIcon = {
                    if (uiState.value.selectedChip == QualitySelector.Tiny.name) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = null
                        )
                    }
                },
                    selected = uiState.value.selectedChip == QualitySelector.Tiny.name,
                    onClick = { viewModel.updateQualitySetting(QualitySelector.Tiny.name) },
                    label = { Text(text = stringResource(id = AppText.tiny)) })
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        clearCacheVisibility.value = !clearCacheVisibility.value
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = stringResource(id = R.string.clear_cache))
                Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null)
            }
        }
    }
}