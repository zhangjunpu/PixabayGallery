package com.junpu.pixabaygallery.ui.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.junpu.pixabaygallery.R
import com.junpu.pixabaygallery.bean.Hit
import com.junpu.pixabaygallery.databinding.FragmentImageBinding

class ImageFragment : Fragment() {

    companion object {
        const val KET_IMAGE = "image"
    }

    private val viewModel: ImageViewModel by viewModels()
    private var image: Hit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        image = arguments?.getParcelable(KET_IMAGE)
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
            data = image
            lifecycleOwner
        }.root
    }

}