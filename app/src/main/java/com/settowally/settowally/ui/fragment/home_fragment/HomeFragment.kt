package com.settowally.settowally.ui.fragment.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.settowally.settowally.R
import com.settowally.settowally.common.Constant.Companion.PER_PAGE_PHOTO_COUNTER
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private var savedPhotosList: List<Photo>? = null
    private var page: Int = 1
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(onItemClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToWallpaperDetailsFragment(it)
            findNavController().navigate(action)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        homeViewModel.getPhotos(page = page, perPage = PER_PAGE_PHOTO_COUNTER)
        savedPhotosList = homeViewModel.localDbResponse.value

        attachSearchObserver()


        binding.searchView.editText.addTextChangedListener(
            afterTextChanged = {
                homeViewModel.searchPhotosWithQuery(it.toString(), page)
            }
        )
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
                }
            )
        }
        binding.searchRecyclerView.adapter=searchAdapter
        binding.imagesRecyclerView.adapter = homeAdapter
        binding.nextButton.setOnClickListener {
            page += 1
            homeViewModel.getPhotos(page, PER_PAGE_PHOTO_COUNTER)
        }
        binding.prevButton.setOnClickListener {
            if (page == 1) {
                page = 1
                Toast.makeText(
                    requireContext(),
                    getString(R.string.you_are_at_first_page), Toast.LENGTH_LONG
                ).show()
            } else {
                page -= 1
                homeViewModel.getPhotos(page, PER_PAGE_PHOTO_COUNTER)
            }
        }
        homeViewModel.photoDataObject.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponseHandler.Success -> {
                    response.data?.let { homeAdapter.submitList(it.photos) }
                }

                is NetworkResponseHandler.Error -> {

                }

                is NetworkResponseHandler.Loading -> {

                }
            }
        }
    }

    private fun attachSearchObserver() {
        homeViewModel.searchPhotoResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponseHandler.Success -> {
                    searchAdapter.submitList(response.data?.photos)
                }

                is NetworkResponseHandler.Loading -> {}
                is NetworkResponseHandler.Error -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}