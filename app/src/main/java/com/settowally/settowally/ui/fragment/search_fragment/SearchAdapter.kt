package com.settowally.settowally.ui.fragment.search_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.databinding.PhotoItemLayoutBinding

class SearchAdapter(
    val onItemClick: (photo: Photo) -> Unit,
    val favoriteButtonClick: (photo: Photo) -> Unit,
) : ListAdapter<Photo, SearchAdapter.SearchViewHolder>(
    SearchAdapterDiffCallback()
) {
    inner class SearchViewHolder(private val binding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = currentList[position]
            Glide.with(binding.root).load(item.src.medium)
                .into(binding.imageViewPerPhoto)
            binding.imageViewPerPhoto.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchAdapter.SearchViewHolder {
        val binding =
            PhotoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        holder.bind(position)
    }
}

class SearchAdapterDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}