package com.settowally.settowally.ui.fragment.wallpaper_details.set_wallpaper_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.settowally.settowally.R
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.model.WallpaperType
import com.settowally.settowally.databinding.FragmentSetWallpaperBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetWallpaperBottomSheetFragment : BottomSheetDialogFragment() {
    private var mBinding: FragmentSetWallpaperBottomSheetBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: SetWallpaperBottomSheetViewModel by viewModels()
    private val args: SetWallpaperBottomSheetFragmentArgs by navArgs()
    private var selectedPhotoQuality: PhotoQuality? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentSetWallpaperBottomSheetBinding.inflate(layoutInflater, container, false)

        val peekHeight =
            resources.getDimensionPixelSize(com.google.android.material.R.dimen.design_bottom_sheet_peek_height_min)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, peekHeight)


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedQuality.collect {
                selectedPhotoQuality = it
            }
        }

        binding.wallpaperSetHomeButton.setOnClickListener {
            viewModel.setWallpaper(
                photoSetQuality(selectedPhotoQuality),
                WallpaperType.HOME_SCREEN,
                requireContext()
            )
            Toast.makeText(
                requireContext(),
                getString(R.string.wallpaper_set_to_home_screen), Toast.LENGTH_LONG
            ).show()
            findNavController().navigateUp()
        }
        binding.wallpaperSetLockButton.setOnClickListener {
            viewModel.setWallpaper(
                photoSetQuality(selectedPhotoQuality),
                WallpaperType.LOCK_SCREEN,
                requireContext()
            )
            Toast.makeText(
                requireContext(),
                getString(R.string.wallpaper_set_to_lock_screen), Toast.LENGTH_LONG
            ).show()
            findNavController().navigateUp()
        }
        binding.wallpaperSetBothButton.setOnClickListener {
            viewModel.setWallpaper(
                photoSetQuality(selectedPhotoQuality),
                WallpaperType.HOME_AND_LOCK_SCREEN,
                requireContext()
            )
            Toast.makeText(
                requireContext(),
                getString(R.string.wallpaper_set_to_system), Toast.LENGTH_LONG
            ).show()
            findNavController().navigateUp()
        }

        return binding.root

    }

    private fun photoSetQuality(selectedPhotoQuality: PhotoQuality?): String {
        return when (selectedPhotoQuality) {
            PhotoQuality.MEDIUM -> {
                args.photo.src.medium
            }

            PhotoQuality.LARGE -> {
                args.photo.src.large
            }

            PhotoQuality.LARGE2X -> {
                args.photo.src.large2x
            }

            PhotoQuality.ORIGINAL -> {
                args.photo.src.original
            }

            PhotoQuality.TINY -> {
                args.photo.src.tiny
            }

            PhotoQuality.LANDSCAPE -> {
                args.photo.src.landscape
            }

            PhotoQuality.SMALL -> {
                args.photo.src.small
            }

            PhotoQuality.PORTRAIT -> {
                args.photo.src.portrait
            }

            else -> {
                args.photo.src.medium
            }
        }
    }

}