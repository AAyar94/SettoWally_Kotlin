package com.settowally.settowally.ui.fragment.home_fragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotosDataModel
import com.settowally.settowally.databinding.PhotoItemLayoutBinding

class HomeFragmentAdapter(
    val savedPhotosList: List<Photo>?,
    val onItemClick: (photo: Photo) -> Unit,
    val favoriteButtonClick: (photo: Photo) -> Unit,
) : RecyclerView.Adapter<HomeFragmentAdapter.HomeViewHolder>() {

    private val photosList = mutableListOf<Photo>()

    inner class HomeViewHolder(private val binding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            Glide.with(binding.root).load(photosList[position].src.medium)
                .into(binding.imageViewPerPhoto)
            binding.isLikedButton.setOnClickListener {
                favoriteButtonClick(photosList[position])
            }
            binding.isLikedButton.setOnClickListener {
                favoriteButtonClick(photosList[position])
                if (savedPhotosList?.contains(photosList[position]) == true){
                    binding.isLikedButton.setColorFilter(Color.RED)
                }else{
                    binding.isLikedButton.setColorFilter(Color.WHITE)
                }
            }
            binding.imageViewPerPhoto.setOnClickListener {
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