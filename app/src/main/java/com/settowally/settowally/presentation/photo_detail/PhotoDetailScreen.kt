package com.settowally.settowally.presentation.photo_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.settowally.settowally.R
import com.settowally.settowally.core.theme.LocalSpacing
import com.settowally.settowally.presentation.photo_detail.PhotoDetailViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun PhotoDetailScreen(photoId: Int?, viewModel: PhotoDetailViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    if (photoId != null) {
        viewModel.getPhotoById(photoId)
    }
    val hazeState = remember { HazeState() }
    val spacing = LocalSpacing.current
val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Black)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (uiState.value.isLoading) {
            CircularProgressIndicator()
        } else {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .haze(state = hazeState, style = HazeStyle(blurRadius = 50.dp)),
                contentScale = ContentScale.Crop,
                model = uiState.value.selectedQualityLink,
                contentDescription = uiState.value.photo?.avgColor
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(24.dp))
                    .hazeChild(hazeState),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceMedium),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(spacing.spaceSmall))
                            Text(text = stringResource(id = R.string.download), color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(2.dp, Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(spacing.spaceSmall))
                            Text(text = stringResource(id = R.string.share), color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(2.dp, Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(spacing.spaceSmall))
                            Text(
                                text = stringResource(id = R.string.photo_details),
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(2.dp, Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Wallpaper,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(spacing.spaceSmall))
                            Text(
                                text = stringResource(id = R.string.set_wallpaper),
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(2.dp, Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        Text(
                            text = stringResource(id = R.string.add_to_favorites),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
