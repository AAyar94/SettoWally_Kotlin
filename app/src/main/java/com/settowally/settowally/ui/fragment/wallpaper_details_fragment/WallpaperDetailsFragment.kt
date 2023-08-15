package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.settowally.settowally.R
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.databinding.AlartDialogPhotoDetailBinding
import com.settowally.settowally.databinding.FragmentWallpaperDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WallpaperDetailsFragment : Fragment() {
    private var mBinding: FragmentWallpaperDetailsBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: WallpaperDetailsViewModel by viewModels()
    private val photoArgs: WallpaperDetailsFragmentArgs by navArgs()
    private var photoQuality: PhotoQuality? = null
    private var loadQuality: String? = null
    private val PERMISSION_REQUEST_CODE = 100
    private val onCompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Handle download completion, update notification, etc.
        }
    }
    private var job: Job? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentWallpaperDetailsBinding.inflate(layoutInflater, container, false)


        setBlurLayer()
        setupButtons()
        return binding.root
    }

    private fun qualityPicker(): String {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.selectedQuality.collect {
                this@WallpaperDetailsFragment.photoQuality = it
            }
        }
        return when (this.photoQuality) {
            PhotoQuality.Landscape -> photoArgs.photo.src.landscape
            PhotoQuality.Large -> photoArgs.photo.src.large
            PhotoQuality.Large2x -> photoArgs.photo.src.large2x
            PhotoQuality.Medium -> photoArgs.photo.src.medium
            PhotoQuality.Original -> photoArgs.photo.src.original
            PhotoQuality.Portrait -> photoArgs.photo.src.portrait
            PhotoQuality.Small -> photoArgs.photo.src.small
            PhotoQuality.Tiny -> photoArgs.photo.src.tiny
            else -> {photoArgs.photo.src.medium}
        }
    }

    private fun setBlurLayer() {
        binding.blurViewOverlay.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.bigImageView.background)
            .setBlurRadius(5f)
        binding.blurViewOverlay.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurViewOverlay.clipToOutline = true
    }

    @SuppressLint("SetTextI18n")
    private fun setupButtons() {
        binding.downloadButton.setOnClickListener {
            /**if (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            ) {
            ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
            )
            } else {
            val action =
            WallpaperDetailsFragmentDirections.actionWallpaperDetailsFragmentToDownloadBottomSheetFragment(photoArgs.photo.src)
            findNavController().navigate(action)
            }*/
            val action =
                WallpaperDetailsFragmentDirections.actionWallpaperDetailsFragmentToDownloadBottomSheetFragment(
                    photoArgs.photo.src
                )
            findNavController().navigate(action)
        }
        binding.shareButton.setOnClickListener {

        }
        binding.detailsButton.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
            val alertDialogView = AlartDialogPhotoDetailBinding.inflate(layoutInflater)
            builder.setView(alertDialogView.root)
            alertDialogView.alertTitle.text = requireContext().getString(R.string.photo_details)
            alertDialogView.photoDetailHeight.text =
                "${requireContext().getString(R.string.height)} ${photoArgs.photo.height}"
            alertDialogView.photoDetailWidth.text =
                "${requireContext().getString(R.string.width)} ${photoArgs.photo.width}"
            alertDialogView.photoDetailPhotographer.text =
                "${requireContext().getString(R.string.photographer)} ${photoArgs.photo.photographer}"
            val dialog = builder.create()
            alertDialogView.buttonCloseAlertDialog.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        binding.setWallpaperButton.setOnClickListener {
            val action =
                WallpaperDetailsFragmentDirections.actionWallpaperDetailsFragmentToSetWallpaperBottomSheetFragment(
                    photo = photoArgs.photo
                )
            findNavController().navigate(action)
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        requireContext().registerReceiver(onCompleteReceiver, filter)

        Glide.with(binding.root.context)
            .load(qualityPicker())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.bigImageView)


    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
        requireContext().unregisterReceiver(onCompleteReceiver)
    }
}