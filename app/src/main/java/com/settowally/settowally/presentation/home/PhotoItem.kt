package com.settowally.settowally.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.settowally.settowally.R
import com.settowally.settowally.domain.model.HomePhotoDataModel

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    homePhotoDataModel: HomePhotoDataModel
) {
    AsyncImage(
        modifier = modifier,
        model = homePhotoDataModel.imgLink,
        contentDescription = stringResource(R.string.description),
        contentScale = ContentScale.FillBounds
    )
}
