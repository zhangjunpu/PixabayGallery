package com.junpu.pixabaygallery.net

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.junpu.log.L
import com.junpu.log.logStackTrace
import com.junpu.pixabaygallery.bean.ImageBean

/**
 *
 * @author junpu
 * @date 2020/7/31
 */
class PixabayDataSource(private val loadStatus: MutableLiveData<LoadStatus>) :
    PageKeyedDataSource<Int, ImageBean>() {

    var retry: (() -> Unit)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageBean>
    ) {
        loadStatus.postValue(LoadStatus.LOADING_INITIAL)
        retry = null
        VolleyManager.instance.run {
            get(hashMapOf(VolleyManager.PAGE to "1")) { isSuccess, result, e ->
                if (isSuccess) {
                    L.vv("1: ${result?.total} / ${result?.totalHits}")
                    loadStatus.postValue(LoadStatus.SUCCESS)
                    callback.onResult(result?.hits ?: emptyList(), null, 2)
                } else {
                    retry = { loadInitial(params, callback) }
                    loadStatus.postValue(LoadStatus.FAILURE)
                    e?.logStackTrace()
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageBean>) {
        loadStatus.postValue(LoadStatus.LOADING)
        retry = null
        VolleyManager.instance.run {
            get(mapOf(VolleyManager.PAGE to "${params.key}")) { isSuccess, result, e ->
                if (isSuccess) {
                    L.vv("${params.key}: ${result?.total} / ${result?.totalHits}")
                    loadStatus.postValue(LoadStatus.SUCCESS)
                    callback.onResult(result?.hits ?: emptyList(), params.key + 1)
                    val total = result?.totalHits ?: 0
                } else {
                    if (e?.javaClass?.name == "com.android.volley.ClientError")
                        loadStatus.postValue(LoadStatus.COMPLETE)
                    else {
                        retry = { loadAfter(params, callback) }
                        loadStatus.postValue(LoadStatus.FAILURE)
                    }
                    e?.logStackTrace()
                }
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageBean>) {
    }
}