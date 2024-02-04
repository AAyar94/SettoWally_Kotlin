package com.settowally.settowally.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.settowally.settowally.R
import com.settowally.settowally.domain.model.GridPhotoDataModel

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    gridPhotoDataModel: GridPhotoDataModel,
    onPhotoClick: (Int) -> Unit
) {
    AsyncImage(
        modifier = modifier.clickable { onPhotoClick(gridPhotoDataModel.id) },
        model = gridPhotoDataModel.imgLink,
        contentDescription = stringResource(R.string.description),
        contentScale = ContentScale.FillBounds
    )
}
