package com.settowally.settowally.ui.fragment.settings_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.settowally.settowally.R
import com.settowally.settowally.common.Constant.Companion.DEVELOPER_PAGE
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

        qualityOptionSetter()
        observeLiveData()


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
                viewModel.setPhotosQuality(PhotoQuality.ORIGINAL)
            }
            large2xQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.LARGE2X)
            }
            largeQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.LARGE)
            }
            landspaceQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.LANDSCAPE)
            }
            portraitQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.PORTRAIT)
            }
            mediumQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.MEDIUM)
            }
            tinyQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.TINY)
            }
            smallQualityChip.setOnClickListener {
                viewModel.setPhotosQuality(PhotoQuality.SMALL)
            }

            developerText.text = getString(R.string.developer_adem_ayar)
            developerText.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DEVELOPER_PAGE))
                startActivity(intent)
            }

            googlePlayButtonImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DEVELOPER_PAGE))
                startActivity(intent)
            }
        }
    }


    private fun observeLiveData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedTheme.collect { savedTheme ->
                binding.darkModeChip.isChecked = savedTheme == Theme.DARK
                binding.lightModeChip.isChecked = savedTheme == Theme.LIGHT
                binding.systemModeChip.isChecked = savedTheme == Theme.SYSTEM
            }
        }
    }

    private fun qualityOptionSetter() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedQuality.collect { savedQuality ->
                when (savedQuality) {

                    PhotoQuality.ORIGINAL -> binding.originalQualityChip.isChecked = true

                    PhotoQuality.LARGE2X -> binding.large2xQualityChip.isChecked = true

                    PhotoQuality.LARGE -> binding.largeQualityChip.isChecked = true

                    PhotoQuality.LANDSCAPE -> binding.landspaceQualityChip.isChecked = true

                    PhotoQuality.PORTRAIT -> binding.portraitQualityChip.isChecked = true

                    PhotoQuality.MEDIUM -> binding.mediumQualityChip.isChecked = true

                    PhotoQuality.TINY -> binding.tinyQualityChip.isChecked = true

                    PhotoQuality.SMALL -> binding.smallQualityChip.isChecked = true

                }
            }
        }
    }
}