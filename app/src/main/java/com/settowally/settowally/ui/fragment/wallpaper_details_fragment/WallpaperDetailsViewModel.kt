package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import androidx.lifecycle.ViewModel
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.PhotoQuality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WallpaperDetailsViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    val selectedQuality: Flow<PhotoQuality> = dataStoreRepository.savedQualitySetting

}