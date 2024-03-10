package com.settowally.settowally.ui.fragment.wallpaper_details.details_bottom_sheet

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.settowally.settowally.R
import com.settowally.settowally.databinding.FragmentDetailsBottomSheetBinding

class DetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private var mBinding: FragmentDetailsBottomSheetBinding? = null
    private val binding get() = mBinding!!
    private val args: DetailsBottomSheetFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentDetailsBottomSheetBinding.inflate(layoutInflater)
        with(binding) {
            infoHeightText.text = "${getString(R.string.height)} : ${args.photo.height}"
            infoWidthText.text = "${getString(R.string.width)} : ${args.photo.width}"
            cardColorInc.setCardBackgroundColor(Color.parseColor(args.photo.avgColor))
            infoAvgColorText.text = "${getString(R.string.average_color)} : ${args.photo.avgColor}"
            infoPhotographerText.text =
                "${getString(R.string.photographer)} :  ${args.photo.photographer}"
            infoAltText.text = "${getString(R.string.description)} : ${args.photo.alt}"

            dialogCloseButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}