package com.junpu.pixabaygallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.junpu.pixabaygallery.R
import com.junpu.pixabaygallery.bean.Hit
import com.junpu.pixabaygallery.databinding.FragmentGalleryItemBinding
import com.junpu.pixabaygallery.ui.image.ImageFragment
import com.junpu.toast.toast

/**
 *
 * @author junpu
 * @date 2020/7/29
 */
class GalleryAdapter : ListAdapter<Hit, GalleryViewHolder>(DIFF) {
    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Hit>() {
            override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            FragmentGalleryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).apply {
                imageView.setOnClickListener {
                    val navController = it.findNavController()
                    navController.navigate(
                        R.id.action_galleryFragment_to_imageFragment,
                        bundleOf(ImageFragment.KET_IMAGE to data)
                    )
                }
            })
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.executePendingBindings()
    }

}

/**
 *
 * @author junpu
 * @date 2020/7/29
 */
class GalleryViewHolder(val binding: FragmentGalleryItemBinding) :
    RecyclerView.ViewHolder(binding.root)