package com.settowally.settowally.ui.fragment.search_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.settowally.settowally.R
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var mBinding: FragmentSearchBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: SearchViewModel by viewModels()
    private val adapter: SearchAdapter by lazy {
        SearchAdapter(onItemClick = {
            val action = SearchFragmentDirections.actionSearchFragmentToWallpaperDetailsFragment(it)
            findNavController().navigate(action)
        })
    }
    private var page: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        binding.searchRecyclerView.adapter = adapter
        attachObserver()
        binding.searchView.queryHint = getString(R.string.search)
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchPhotosWithQuery(query = query, page = page)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    private fun attachObserver() {
        viewModel.searchPhotoResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponseHandler.Success -> {
                    adapter.submitList(response.data?.photos)
                }

                is NetworkResponseHandler.Loading -> {}
                is NetworkResponseHandler.Error -> {}
            }
        }
    }
}