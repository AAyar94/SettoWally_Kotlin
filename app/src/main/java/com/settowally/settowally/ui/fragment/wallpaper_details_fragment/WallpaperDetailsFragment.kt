package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.settowally.settowally.BuildConfig
import com.settowally.settowally.R
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.databinding.FragmentWallpaperDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentWallpaperDetailsBinding.inflate(layoutInflater, container, false)
        getFavoriteImageList()

        setBlurLayer()
        setupButtons()
        getQualityOption()
        return binding.root
    }

    private fun getFavoriteImageList() {
        viewModel.getFavoritePhotos()
    }

    private fun getQualityOption() {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 6f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedQuality.collect { quality ->
                urlParser(quality).let {
                    Glide.with(binding.root)
                        .load(it)
                        .placeholder(circularProgressDrawable)
                        .into(binding.bigImageView)
                }
            }
        }
    }

    private fun urlParser(quality: PhotoQuality): String {
        val url = photoArgs.photo.src
        return when (quality) {
            PhotoQuality.ORIGINAL -> url.original
            PhotoQuality.LARGE2X -> url.large2x
            PhotoQuality.LARGE -> url.large
            PhotoQuality.MEDIUM -> url.medium
            PhotoQuality.SMALL -> url.small
            PhotoQuality.TINY -> url.tiny
            PhotoQuality.LANDSCAPE -> url.landscape
            PhotoQuality.PORTRAIT -> url.portrait
        }
    }

    private fun setBlurLayer() {
        binding.blurViewOverlay.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.bigImageView.background)
            .setBlurRadius(15f)
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
            shareImage(getBitmapFromImageView(binding.bigImageView))
        }
        binding.detailsButton.setOnClickListener {
            val action =
                WallpaperDetailsFragmentDirections.actionWallpaperDetailsFragmentToDetailsBottomSheetFragment(
                    photo = photoArgs.photo
                )
            findNavController().navigate(action)
        }
        binding.setWallpaperButton.setOnClickListener {
            val action =
                WallpaperDetailsFragmentDirections.actionWallpaperDetailsFragmentToSetWallpaperBottomSheetFragment(
                    photo = photoArgs.photo
                )
            findNavController().navigate(action)
        }

        if (viewModel.isPhotoLiked(photoArgs.photo, viewModel.localDbResponse.value)) {
            binding.manageFavoritesButton.text = getString(R.string.delete_from_favorites)
            binding.manageFavoritesButton.setIconResource(R.drawable.ic_delete)
        } else {
            binding.manageFavoritesButton.text = getString(R.string.add_to_favorites)
            binding.manageFavoritesButton.setIconResource(R.drawable.ic_favorite_filled)
        }

        binding.manageFavoritesButton.setOnClickListener {
            if (viewModel.isPhotoLiked(photoArgs.photo, viewModel.localDbResponse.value)) {
                viewModel.deletePhotoFromDb(photoArgs.photo)
            } else {
                viewModel.savePhotoToDb(photoArgs.photo)
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        requireContext().registerReceiver(onCompleteReceiver, filter)
    }


    private fun getBitmapFromImageView(imageView: ImageView): BitmapDrawable {
        return imageView.drawable as BitmapDrawable
    }

    // Function to share the image using a Share Intent
    private fun shareImage(bitmapDrawable: BitmapDrawable) {
        val bitmap = bitmapDrawable.bitmap

        val cachePath = File(requireContext().cacheDir, "images")
        cachePath.mkdirs()

        // Create a unique image file in the cache directory
        val file = File(cachePath, "image.png")
        val fileOutputStream = FileOutputStream(file)

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fileOutputStream.close()
        }

        val fileUri = FileProvider.getUriForFile(
            requireContext(),
            "com.settowally.settowally.fileprovider",  // Replace with your package name
            file
        )

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, fileUri)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            getString(com.settowally.settowally.R.string.HeyCheckMyApp) + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
        requireContext().unregisterReceiver(onCompleteReceiver)
    }
}