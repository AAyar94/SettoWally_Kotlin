package com.settowally.settowally.ui.fragment.favorites_fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.settowally.settowally.R
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.databinding.PhotoItemLayoutBinding

class FavoritesAdapter(val onItemClick: (photo: Photo) -> Unit) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private val favoritePhotosList = mutableListOf<Photo>()

    inner class FavoritesViewHolder(private val binding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            Log.d("FavoritesFragment", favoritePhotosList.size.toString())
            Glide.with(binding.root).load(favoritePhotosList[position].src.medium)
                .into(binding.imageViewPerPhoto)
            binding.isLikedButton.setOnClickListener {
                /**     LIKE FUNCTION       */
            }
            if (favoritePhotosList[position].liked) {
                Glide.with(binding.root).load(R.drawable.ic_favorite_filled)
                    .into(binding.isLikedButton)
            }
            binding.root.setOnClickListener {
                onItemClick(favoritePhotosList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val favoriteBinding =
            PhotoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(favoriteBinding)
    }

    override fun getItemCount(): Int {
        return favoritePhotosList.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(dataModel: List<Photo>) {
        favoritePhotosList.addAll(dataModel)
        notifyDataSetChanged()
    }

}