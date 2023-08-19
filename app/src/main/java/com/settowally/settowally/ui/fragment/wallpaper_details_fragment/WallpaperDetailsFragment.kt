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
import com.bumptech.glide.Glide
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


        setBlurLayer()
        setupButtons()
        getQualityOption()
        return binding.root
    }

    private fun getQualityOption() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedQuality.collect { quality ->
                urlParser(quality).let {
                    Glide.with(binding.root)
                        .load(it)
                        .into(binding.bigImageView)
                }
            }
        }
    }

    fun urlParser(quality: PhotoQuality): String {
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
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        requireContext().registerReceiver(onCompleteReceiver, filter)

        /*Glide.with(binding.root.context)
            .load(qualityPicker(viewModel.selectedQuality))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.bigImageView)*/
    }

    /* private fun qualityPicker(selectedQuality: Flow<PhotoQuality>): String {
         return when (selectedQuality.toString()) {
             "Landscape" -> photoArgs.photo.src.landscape
             "Large" -> photoArgs.photo.src.large
             "Large2x" -> photoArgs.photo.src.large2x
             "Medium" -> photoArgs.photo.src.medium
             "Original" -> photoArgs.photo.src.original
             "Portrait" -> photoArgs.photo.src.portrait
             "Small" -> photoArgs.photo.src.small
             "Tiny" -> photoArgs.photo.src.tiny
             else -> {
                 photoArgs.photo.src.medium
             }
         }
     }*/

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

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)

        startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
        requireContext().unregisterReceiver(onCompleteReceiver)
    }
}