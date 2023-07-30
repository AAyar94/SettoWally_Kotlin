package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.settowally.settowally.R
import com.settowally.settowally.databinding.FragmentWallpaperDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.InputStream

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

        binding.blurViewOverlay.setupWith(binding.root,  RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.bigImageView.background)
            .setBlurRadius(5f)
        binding.blurViewOverlay.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurViewOverlay.clipToOutline = true;

        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        Glide.with(binding.root).load(photoArgs.photo.src.original).placeholder(circularProgressDrawable).into(binding.bigImageView)

        setupButtons()
        return binding.root
    }

    private fun setupButtons() {
        binding.downloadButton.setOnClickListener {

        }
        binding.shareButton.setOnClickListener {

        }
        binding.detailsButton.setOnClickListener {

        }
        binding.setWallpaperButton.setOnClickListener {

        }
    }



    override fun onDestroy() {
        super.onDestroy()
        mBinding=null
    }

}