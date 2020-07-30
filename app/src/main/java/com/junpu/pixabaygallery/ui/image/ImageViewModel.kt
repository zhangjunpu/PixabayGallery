package com.junpu.pixabaygallery.ui.image

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junpu.pixabaygallery.bean.ImageBean

class ImageViewModel : ViewModel() {
    val imageList by lazy { MutableLiveData<List<ImageBean>>() }
    val imageIndex by lazy { MutableLiveData<Int>(0) }
}