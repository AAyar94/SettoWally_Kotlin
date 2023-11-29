package com.settowally.settowally.ui.fragment.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.settowally.settowally.common.Constant.Companion.PER_PAGE_PHOTO_COUNTER
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.common.PaginationScrollListener
import com.settowally.settowally.common.setInvisible
import com.settowally.settowally.common.setVisible
import com.settowally.settowally.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    var isloading = false
    val page = 1
    val searchPage = 1
    var currentPage = page
    var isLastPage = false
    var searchCurrentPage = 1
    var query = ""
    private val homeAdapter: HomeFragmentAdapter by lazy {
        HomeFragmentAdapter { photo ->
            val action = HomeFragmentDirections.actionHomeFragmentToWallpaperDetailsFragment(photo)
            findNavController().navigate(action)
        }
    }
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter { photo ->
            val action = HomeFragmentDirections.actionHomeFragmentToWallpaperDetailsFragment(photo)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHomeObservers()

        setupRecyclerView()
        setupSearchRecyclerView()
        callFirstPage()
        searchFirstPage()
    }

    private fun setupSearchRecyclerView() {
        val searchLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchRecyclerView.layoutManager = searchLayoutManager
        binding.searchRecyclerView.adapter = searchAdapter
        binding.searchRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(searchLayoutManager) {
            override fun loadMoreItems() {
                isloading = true
                searchCurrentPage++

                homeViewModel.searchPhotosWithQuery(query, searchCurrentPage)
            }

            override fun getTotalPageCount(): Int {
                return getTotalPageCount()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isloading
            }

        })
    }

    private fun searchFirstPage() {
        binding.searchView.editText.addTextChangedListener { queryText ->
            homeViewModel.searchPhotosWithQuery(queryText.toString(), searchCurrentPage)
            query = queryText.toString()
        }
    }

    private fun setupRecyclerView() {
        val homeLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.imagesRecyclerView.layoutManager = homeLayoutManager
        binding.imagesRecyclerView.adapter = homeAdapter
        binding.imagesRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(homeLayoutManager) {
            override fun loadMoreItems() {
                isloading = true
                currentPage++

                homeViewModel.getPhotos(currentPage)
            }

            override fun getTotalPageCount(): Int {
                return getTotalPageCount()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isloading
            }
        })
    }

    private fun initHomeObservers() {
        homeViewModel.photoDataObject.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResponseHandler.Error -> {
                    binding.imagesRecyclerView.setInvisible()
                    binding.errorImageView.setVisible()
                    binding.errorTextView.setVisible()
                    binding.homeProgressBar.setInvisible()
                    isloading = false
                }

                is NetworkResponseHandler.Loading -> {
                    binding.imagesRecyclerView.setInvisible()
                    binding.errorImageView.setInvisible()
                    binding.errorTextView.setInvisible()
                    isloading = true
                    binding.homeProgressBar.setVisible()
                }

                is NetworkResponseHandler.Success -> {
                    binding.imagesRecyclerView.setVisible()
                    binding.errorImageView.setInvisible()
                    binding.errorTextView.setInvisible()
                    binding.homeProgressBar.setInvisible()
                    val newList = homeAdapter.currentList + result.data?.photos!!
                    homeAdapter.submitList(newList)
                    isloading = false
                }
            }
        }
        homeViewModel.searchPhotoResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResponseHandler.Error -> {
                    binding.searchRecyclerView.setInvisible()
                    binding.errorImageView.setVisible()
                    binding.errorTextView.setVisible()
                    binding.homeProgressBar.setInvisible()
                    isloading = false
                }

                is NetworkResponseHandler.Loading -> {
                    binding.searchRecyclerView.setInvisible()
                    binding.errorImageView.setInvisible()
                    binding.errorTextView.setInvisible()
                    binding.homeProgressBar.setVisible()
                    isloading = true
                }

                is NetworkResponseHandler.Success -> {
                    binding.searchRecyclerView.setVisible()
                    binding.errorImageView.setInvisible()
                    binding.errorTextView.setInvisible()
                    binding.homeProgressBar.setInvisible()
                    val newSearchList = searchAdapter.currentList + result.data?.photos!!
                    searchAdapter.submitList(newSearchList)
                    isloading = false
                }
            }
        }
    }

    private fun callFirstPage() {
        homeViewModel.getPhotos(page)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}