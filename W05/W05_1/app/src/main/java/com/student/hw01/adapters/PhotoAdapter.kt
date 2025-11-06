package com.student.hw01.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.student.hw01.databinding.ItemPhotoCardBinding
import com.student.hw01.models.Photo

class PhotoAdapter(
    private val photoList: List<Photo>,
    private val onPhotoClick: (Photo) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(val binding: ItemPhotoCardBinding) : 
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoCardBinding.inflate(
            LayoutInflater.from(parent.context), 
            parent, 
            false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photoList[position]

        Glide.with(holder.itemView.context)
            .load(photo.uri)
            .centerCrop()
            .into(holder.binding.ivPhoto)

        holder.itemView.setOnClickListener {
            onPhotoClick(photo)
        }
    }

    override fun getItemCount(): Int = photoList.size
}
