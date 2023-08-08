package com.settowally.settowally.ui.fragment.settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.model.Theme
import com.settowally.settowally.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var mBinding: FragmentSettingsBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            /**     Theme Picker OnClick Listeners      */
            darkModeChip.setOnClickListener {
                viewModel.saveTheme(Theme.DARK)
            }

            lightModeChip.setOnClickListener {
                viewModel.saveTheme(Theme.LIGHT)
            }

            systemModeChip.setOnClickListener {
                viewModel.saveTheme(Theme.SYSTEM)
            }

            /**      Quality Picker OnClick Listeners        */
            originalQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.Original)
            }
            large2xQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.Large2x)
            }
            largeQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.Large)
            }
            landspaceQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.Landscape)
            }
            portraitQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.Portrait)
            }
            mediumQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.Medium)
            }
            tinyQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.Tiny)
            }
            smallQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.Small)
            }
        }



        observeLiveData()
    }

    private fun observeLiveData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedTheme.collect { savedTheme ->
                // Kontrolke
                binding.darkModeChip.isChecked = savedTheme == Theme.DARK
                binding.lightModeChip.isChecked = savedTheme == Theme.LIGHT
                binding.systemModeChip.isChecked = savedTheme == Theme.SYSTEM
            }
            viewModel.selectedQuality.collect { savedQuality ->
                binding.originalQualityChip.isChecked = savedQuality == PhotoQuality.Original
                binding.large2xQualityChip.isChecked = savedQuality == PhotoQuality.Large2x
                binding.largeQualityChip.isChecked = savedQuality == PhotoQuality.Large
                binding.landspaceQualityChip.isChecked = savedQuality == PhotoQuality.Landscape
                binding.portraitQualityChip.isChecked = savedQuality == PhotoQuality.Portrait
                binding.mediumQualityChip.isChecked = savedQuality == PhotoQuality.Medium
                binding.tinyQualityChip.isChecked = savedQuality == PhotoQuality.Tiny
                binding.smallQualityChip.isChecked = savedQuality == PhotoQuality.Small
            }
        }
    }
}