package com.junpu.pixabaygallery.net

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junpu.log.L
import com.junpu.log.logStackTrace
import com.junpu.pixabaygallery.App
import com.junpu.pixabaygallery.bean.Hit
import com.junpu.pixabaygallery.bean.StatusBean

/**
 * Volley
 * @author junpu
 * @date 2020/7/29
 */
class VolleyManager private constructor(context: Context) {

    companion object {
        private const val BASE_URL = "https://pixabay.com/api/"
        private const val PIXABAY_KEY = "17677122-3230817d1fc1747f3a718c9fa"
        val instance by lazy { VolleyManager(App.app) }
    }

    var queue: RequestQueue? = null
    val gson by lazy { Gson() }

    init {
        queue = Volley.newRequestQueue(context)
    }

    fun get(map: Map<String, String?>? = null, callbacks: (List<Hit>?) -> Unit) {
        val url = newRequestUrl(map)
        L.vv(url)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                val result = gson.fromJson(it, StatusBean::class.java)
                callbacks(result.hits)
            },
            {
                it.logStackTrace()
            }
        )
        queue?.add(request)
    }

    private fun newRequestUrl(map: Map<String, String?>?): String {
        val sb = StringBuilder(BASE_URL)
        sb.append("?key=").append(PIXABAY_KEY)
        map?.forEach {
            sb.append("&").append(it.key).append("=").append(it.value ?: "")
        }
        sb.append("&safesearch=false&q=")
        return sb.toString()
    }

}