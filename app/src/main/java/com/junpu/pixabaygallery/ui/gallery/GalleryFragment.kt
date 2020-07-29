package com.junpu.pixabaygallery.ui.gallery

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.junpu.pixabaygallery.R
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private val viewModel by viewModels<GalleryViewModel>()
    private val adapter by lazy { GalleryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.run {
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                    // 防止位置变换
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                }
            adapter = this@GalleryFragment.adapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val spanIndex =
                        (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
                    val space = (resources.displayMetrics.density * 2).toInt()
                    val rect = Rect(0, 0, space, space)
                    if (spanIndex % 2 != 0) rect.right = 0
                    outRect.set(rect)
                }
            })
        }
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.loadData()
    }

}