package com.junpu.pixabaygallery.ui.image

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.junpu.log.L
import com.junpu.pixabaygallery.R
import com.junpu.pixabaygallery.bean.ImageBean
import com.junpu.pixabaygallery.databinding.FragmentImageBinding
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : Fragment() {

    companion object {
        const val IMAGE_LIST = "image_list"
        const val IMAGE_INDEX = "image_index"
    }

    private val viewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            imageList.value = arguments?.getParcelableArrayList(IMAGE_LIST)
            imageIndex.value = arguments?.getInt(IMAGE_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentImageBinding>(
            inflater,
            R.layout.fragment_image,
            container,
            false
        ).apply {
            vm = viewModel
            lifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager.run {
            adapter = ImageAdapter().apply {
                submitList(viewModel.imageList.value)
            }
            setCurrentItem(viewModel.imageIndex.value ?: 0, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.imageIndex.value = position + 1
                }
            })
        }
    }

}