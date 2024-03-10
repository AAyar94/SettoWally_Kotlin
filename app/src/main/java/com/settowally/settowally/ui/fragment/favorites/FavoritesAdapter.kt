package com.settowally.settowally.ui.fragment.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.databinding.PhotoItemLayoutBinding

class FavoritesAdapter(val onItemClick: (photo: Photo) -> Unit) :
    ListAdapter<Photo, FavoritesAdapter.FavoritesViewHolder>(
        FavoritePhotosDiffCallback()
    ) {

    inner class FavoritesViewHolder(private val binding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val currentItem = currentList[position]
            Log.d("FavoritesFragment", currentList.size.toString())
            Glide.with(binding.root).load(currentItem.src.medium)
                .into(binding.imageViewPerPhoto)
            binding.root.setOnClickListener {
                onItemClick(currentItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val favoriteBinding =
            PhotoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(favoriteBinding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(position)
    }
}

class FavoritePhotosDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}