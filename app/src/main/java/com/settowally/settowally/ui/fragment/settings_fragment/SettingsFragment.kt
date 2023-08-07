package com.settowally.settowally.ui.fragment.settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
            darkModeChip.setOnClickListener {
                viewModel.saveTheme(Theme.DARK)
            }

            lightModeChip.setOnClickListener {
                viewModel.saveTheme(Theme.LIGHT)
            }

            systemModeChip.setOnClickListener {
                viewModel.saveTheme(Theme.SYSTEM)
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
        }
    }
}