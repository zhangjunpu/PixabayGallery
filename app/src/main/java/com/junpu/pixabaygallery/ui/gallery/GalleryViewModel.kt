package com.junpu.pixabaygallery.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junpu.pixabaygallery.bean.ImageBean
import com.junpu.pixabaygallery.net.VolleyManager

class GalleryViewModel : ViewModel() {

    val imageList: MutableLiveData<List<ImageBean>> by lazy { MutableLiveData<List<ImageBean>>() }

    fun loadData() {
        VolleyManager.instance.get(mapOf("per_page" to "200")) {
            imageList.value = it
        }
    }
}