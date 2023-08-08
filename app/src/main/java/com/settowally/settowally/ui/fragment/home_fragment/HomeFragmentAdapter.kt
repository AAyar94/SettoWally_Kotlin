package com.settowally.settowally.ui.fragment.home_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.settowally.settowally.R
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.model.PhotosDataModel
import com.settowally.settowally.databinding.PhotoItemLayoutBinding

class HomeFragmentAdapter(
    val quality: String?,
    val onItemClick: (photo: Photo) -> Unit,
    val favoriteButtonClick: (photo: Photo) -> Unit,
) : RecyclerView.Adapter<HomeFragmentAdapter.HomeViewHolder>() {

    private val photosList = mutableListOf<Photo>()

    inner class HomeViewHolder(private val binding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(position: Int) {
            val requestQuality = when (quality) {
                "Tiny" -> photosList[layoutPosition].src.tiny
                "Small" -> photosList[layoutPosition].src.small
                "Medium" -> photosList[layoutPosition].src.medium
                "Portrait" -> photosList[layoutPosition].src.portrait
                "Landscape" -> photosList[layoutPosition].src.landscape
                "Large" -> photosList[layoutPosition].src.large
                "Large2x" -> photosList[layoutPosition].src.large2x
                "Original" -> photosList[layoutPosition].src.original
                else -> photosList[layoutPosition].src.medium
            }
            Glide.with(binding.root).load(requestQuality)
                .into(binding.imageViewPerPhoto)
            binding.isLikedButton.setOnClickListener {
                favoriteButtonClick(photosList[position])
            }
            if (photosList[position].liked) {
                Glide.with(binding.root).load(R.drawable.ic_favorite_filled)
                    .into(binding.isLikedButton)
            }
            binding.root.setOnClickListener {
                onItemClick(photosList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            PhotoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(dataModel: PhotosDataModel) {
        photosList.addAll(dataModel.photos)
        notifyDataSetChanged()
    }

}