package com.settowally.settowally.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val _isDarkMode = MutableStateFlow<String?>(null)
    val isDarkMode: StateFlow<String?> get() = _isDarkMode

    fun getSelectedTheme() {
        viewModelScope.launch {
            val fetchedData = dataStoreRepository.savedQualitySetting
            _isDarkMode.value = fetchedData.toString()
        }

    }

}