package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import android.app.WallpaperManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.model.WallpaperType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class WallpaperDetailsViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val selectedQuality : Flow<PhotoQuality> = dataStoreRepository.savedQualitySetting


}