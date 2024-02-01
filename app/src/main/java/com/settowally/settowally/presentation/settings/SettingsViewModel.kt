package com.settowally.settowally.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        readQualitySettings()
    }

    private fun readQualitySettings() {
        viewModelScope.launch {
            val qualityOption = preferences.readQualitySettings()
            _uiState.update {
                it.copy(
                    selectedChip = qualityOption
                )
            }
        }
    }

    fun updateQualitySetting(string: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedChip = string
                )
            }
            preferences.saveQualitySettings(string)
        }
    }

}