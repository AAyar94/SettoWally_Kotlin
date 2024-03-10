package com.settowally.settowally.ui.fragment.home_fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.settowally.settowally.R
import com.settowally.settowally.common.Constant.Companion.PER_PAGE_PHOTO_COUNTER
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.common.PaginationScrollListener
import com.settowally.settowally.common.setInvisible
import com.settowally.settowally.common.setVisible
import com.settowally.settowally.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    var isLoading = false
    val page = 1
    val searchPage = 1
    var currentPage = page
    var isLastPage = false
    var searchCurrentPage = 1
    var query = ""
    var homeLayoutManager: GridLayoutManager? = null
    var searchLayoutManager: GridLayoutManager? = null
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
        val isTablet = requireContext().resources.getBoolean(R.bool.tablet)
        if (isTablet) {
            searchLayoutManager = GridLayoutManager(requireContext(), 4)
        } else {
            searchLayoutManager = GridLayoutManager(requireContext(), 2)
        }
        binding.searchRecyclerView.layoutManager = searchLayoutManager
        binding.searchRecyclerView.adapter = searchAdapter
        binding.searchRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(searchLayoutManager!!) {
            override fun loadMoreItems() {
                isLoading = true
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
                return isLoading
            }

        })
    }

    private val debouncePeriod = 500L // Adjust this delay as needed

    private val handler = Handler(Looper.getMainLooper())
    private var searchJob: Job? = null

    private fun searchFirstPage() {
        binding.searchView.editText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(editable: Editable?) {
                    // Cancel the previous search job if it exists
                    searchJob?.cancel()

                    // Start a new coroutine to debounce the search
                    searchJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(debouncePeriod)
                        // Get the query text after the debounce delay
                        val queryText = editable?.toString() ?: ""
                        homeViewModel.searchPhotosWithQuery(queryText, searchCurrentPage)
                        query = queryText
                    }
                }
            }
        )
    }

    private fun setupRecyclerView() {
        val isTablet = requireContext().resources.getBoolean(R.bool.tablet)
        homeLayoutManager = if (isTablet) {
            GridLayoutManager(requireContext(), 4)
        } else {
            GridLayoutManager(requireContext(), 2)
        }
        binding.imagesRecyclerView.layoutManager = homeLayoutManager
        binding.imagesRecyclerView.adapter = homeAdapter
        binding.imagesRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(homeLayoutManager!!) {
            override fun loadMoreItems() {
                isLoading = true
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
                return isLoading
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
                    isLoading = false
                }

                is NetworkResponseHandler.Loading -> {
                    binding.imagesRecyclerView.setInvisible()
                    binding.errorImageView.setInvisible()
                    binding.errorTextView.setInvisible()
                    isLoading = true
                    binding.homeProgressBar.setVisible()
                }

                is NetworkResponseHandler.Success -> {
                    binding.imagesRecyclerView.setVisible()
                    binding.errorImageView.setInvisible()
                    binding.errorTextView.setInvisible()
                    binding.homeProgressBar.setInvisible()
                    val newList = homeAdapter.currentList + result.data?.photos!!
                    homeAdapter.submitList(newList)
                    isLoading = false
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
                    isLoading = false
                }

                is NetworkResponseHandler.Loading -> {
                    binding.searchRecyclerView.setInvisible()
                    binding.errorImageView.setInvisible()
                    binding.errorTextView.setInvisible()
                    binding.homeProgressBar.setVisible()
                    isLoading = true
                }

                is NetworkResponseHandler.Success -> {
                    binding.searchRecyclerView.setVisible()
                    binding.errorImageView.setInvisible()
                    binding.errorTextView.setInvisible()
                    binding.homeProgressBar.setInvisible()
                    val newSearchList = result.data?.photos!!
                    searchAdapter.submitList(newSearchList)
                    isLoading = false
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