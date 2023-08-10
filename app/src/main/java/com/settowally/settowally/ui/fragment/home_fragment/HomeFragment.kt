package com.settowally.settowally.ui.fragment.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.settowally.settowally.common.Constant.Companion.PER_PAGE_PHOTO_COUNTER
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        /**viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.selectedQuality.collect { savedQuality ->
                photoQuality = savedQuality
            }
        }*/
        homeViewModel.getPhotos(page = 1, perPage = PER_PAGE_PHOTO_COUNTER)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeAdapter: HomeFragmentAdapter by lazy {
            HomeFragmentAdapter(
                onItemClick = { photo ->
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToWallpaperDetailsFragment(photo)
                    findNavController().navigate(action)
                },
                favoriteButtonClick = { photo ->
                    homeViewModel.savePhotoToDb(photo)
                }
            )
        }
        binding.imagesRecyclerView.adapter = homeAdapter

        homeViewModel.photoDataObject.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponseHandler.Success -> {
                    response.data?.let { homeAdapter.setData(it) }
                }

                is NetworkResponseHandler.Error -> {

                }

                is NetworkResponseHandler.Loading -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }


}