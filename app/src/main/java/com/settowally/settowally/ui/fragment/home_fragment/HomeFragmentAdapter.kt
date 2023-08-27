package com.settowally.settowally.ui.fragment.home_fragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.databinding.PhotoItemLayoutBinding

class HomeFragmentAdapter(
    val onItemClick: (photo: Photo) -> Unit,
) : ListAdapter<Photo, HomeFragmentAdapter.HomeViewHolder>(
    PhotosDiffCallback()
) {
    inner class HomeViewHolder(private val binding: PhotoItemLayoutBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            PhotoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(position)
    }

}

class PhotosDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}