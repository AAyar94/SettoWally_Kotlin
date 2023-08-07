package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.settowally.settowally.R
import com.settowally.settowally.data.model.WallpaperType
import com.settowally.settowally.databinding.AlartDialogPhotoDetailBinding
import com.settowally.settowally.databinding.AlertDialogSetWallpaperBinding
import com.settowally.settowally.databinding.FragmentWallpaperDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WallpaperDetailsFragment : Fragment() {
    private var mBinding: FragmentWallpaperDetailsBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: WallpaperDetailsViewModel by viewModels()
    private val photoArgs: WallpaperDetailsFragmentArgs by navArgs()

    private var job: Job? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentWallpaperDetailsBinding.inflate(layoutInflater, container, false)

        job = GlobalScope.launch(Dispatchers.Main) {

            delay(400) // :)

            Glide.with(binding.root.context)
                .load(photoArgs.photo.src.medium)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressCircular.visibility = View.GONE
                        binding.errorImage.visibility = View.VISIBLE
                        binding.blurViewOverlay.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressCircular.visibility = View.GONE
                        binding.errorImage.visibility = View.GONE
                        binding.blurViewOverlay.visibility = View.VISIBLE
                        return false
                    }

                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.bigImageView)
        }


        setBlurLayer()
        setupButtons()
        return binding.root
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
            val builder = MaterialAlertDialogBuilder(requireContext())
            val dialogView = AlertDialogSetWallpaperBinding.inflate(layoutInflater)
            builder.setView(dialogView.root)
            dialogView.wallpaperSetHomeButton.setOnClickListener {
                viewModel.setWallpaper(
                    photoArgs.photo.src.medium,
                    WallpaperType.HOME_SCREEN,
                    requireContext()
                )
            }
            dialogView.wallpaperSetLockButton.setOnClickListener {
                viewModel.setWallpaper(
                    photoArgs.photo.src.medium,
                    WallpaperType.LOCK_SCREEN,
                    requireContext()
                )
            }
            dialogView.wallpaperSetBothButton.setOnClickListener {
                viewModel.setWallpaper(
                    photoArgs.photo.src.medium,
                    WallpaperType.HOME_AND_LOCK_SCREEN,
                    requireContext()
                )
            }
            val dialog = builder.create()
            dialog.show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
        job?.cancel()
    }
}