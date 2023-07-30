package com.settowally.settowally.ui.fragment.settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.settowally.settowally.databinding.FragmentSettingsBinding


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


}