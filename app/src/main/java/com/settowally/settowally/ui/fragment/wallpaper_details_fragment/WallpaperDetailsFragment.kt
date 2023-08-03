package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.settowally.settowally.R
import com.settowally.settowally.databinding.AlartDialogPhotoDetailBinding
import com.settowally.settowally.databinding.FragmentWallpaperDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur

@AndroidEntryPoint
class WallpaperDetailsFragment : Fragment() {
    private var mBinding: FragmentWallpaperDetailsBinding? = null
    private val binding get() = mBinding!!
    private val photoArgs: WallpaperDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentWallpaperDetailsBinding.inflate(layoutInflater, container, false)

        binding.blurViewOverlay.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.bigImageView.background)
            .setBlurRadius(5f)
        binding.blurViewOverlay.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurViewOverlay.clipToOutline = true;

        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        Glide.with(binding.root).load(photoArgs.photo.src.original)
            .placeholder(circularProgressDrawable).into(binding.bigImageView)

        setupButtons()
        return binding.root
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

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}