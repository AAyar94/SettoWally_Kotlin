package com.settowally.settowally.ui.fragment.settings_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {



    /* fun readDarkModeSetting() : Int{


      }*/


    fun setDarKMode(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDarkModeOption(text)
        }
    }

    fun setPhotosQuality(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveQualityOption(text)
        }
    }

}