package com.junpu.pixabaygallery.ui.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.junpu.pixabaygallery.bean.ImageBean
import com.junpu.pixabaygallery.databinding.FragmentImageItemBinding

/**
 *
 * @author junpu
 * @date 2020/7/30
 */
class ImageAdapter : ListAdapter<ImageBean, ImageViewHolder>(DIFF) {
    object DIFF : DiffUtil.ItemCallback<ImageBean>() {
        override fun areItemsTheSame(oldItem: ImageBean, newItem: ImageBean): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageBean, newItem: ImageBean): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            FragmentImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.run {
            data = getItem(position)
            executePendingBindings()
        }
    }
}

class ImageViewHolder(val binding: FragmentImageItemBinding) : RecyclerView.ViewHolder(binding.root)