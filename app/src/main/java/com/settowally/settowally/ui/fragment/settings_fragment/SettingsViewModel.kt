package com.settowally.settowally.ui.fragment.settings_fragment

import androidx.lifecycle.ViewModel
import com.settowally.settowally.repository.data_store.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

  /* fun readDarkModeSetting() : Int{


    }*/


    fun setDarKMode(text:String) {

    }

    fun setPhotosQuality(text: String) {

    }

}