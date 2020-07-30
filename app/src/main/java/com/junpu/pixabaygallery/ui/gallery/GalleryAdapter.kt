package com.junpu.pixabaygallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.junpu.pixabaygallery.R
import com.junpu.pixabaygallery.bean.ImageBean
import com.junpu.pixabaygallery.databinding.FragmentGalleryItemBinding
import com.junpu.pixabaygallery.ui.image.ImageFragment
import kotlinx.android.synthetic.main.fragment_gallery_item.view.*

/**
 *
 * @author junpu
 * @date 2020/7/29
 */
class GalleryAdapter : ListAdapter<ImageBean, GalleryViewHolder>(DIFF) {
    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ImageBean>() {
            override fun areItemsTheSame(oldItem: ImageBean, newItem: ImageBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageBean, newItem: ImageBean): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val holder = GalleryViewHolder(
            FragmentGalleryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        holder.itemView.imageView.setOnClickListener {
            val navController = it.findNavController()
            navController.navigate(
                R.id.action_galleryFragment_to_imageFragment,
                bundleOf(
                    ImageFragment.IMAGE_LIST to currentList.toMutableList(),
                    ImageFragment.IMAGE_INDEX to holder.adapterPosition
                )
            )
        }
        return holder
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