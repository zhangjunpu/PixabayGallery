package com.junpu.pixabaygallery.net

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junpu.log.L
import com.junpu.log.logStackTrace
import com.junpu.pixabaygallery.App
import com.junpu.pixabaygallery.bean.ImageBean
import com.junpu.pixabaygallery.bean.StatusBean
import kotlin.math.ceil

/**
 * Volley
 * @author junpu
 * @date 2020/7/29
 */
class VolleyManager private constructor(context: Context) {

    companion object {
        private const val BASE_URL = "https://pixabay.com/api/"
        private const val PIXABAY_KEY = "17677122-3230817d1fc1747f3a718c9fa"
        const val PAGE_SIZE = 50
        const val PAGE = "page"
        val instance by lazy { VolleyManager(App.app) }
    }

    var queue: RequestQueue? = null
    val gson by lazy { Gson() }

    init {
        queue = Volley.newRequestQueue(context)
    }

    fun get(
        map: Map<String, String?>? = null,
        callback: (isSuccess: Boolean, result: StatusBean?, e: VolleyError?) -> Unit
    ) {
        val url = newRequestUrl(map)
        L.vv(url)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                val result = gson.fromJson(it, StatusBean::class.java)
                callback(true, result, null)
            },
            {
                it.logStackTrace()
                callback(false, null, it)
            }
        )
        queue?.add(request)
    }

    /**
     * 最大页码
     */
    val maxPages = { total: Int -> ceil(total.toFloat() / PAGE_SIZE).toInt() }

    /**
     * q
     * URL编码的搜索词，如果省略，则返回所有图片，不能超过100个字符。
     * 例: "yellow+flower"
     *
     * lang
     * 要搜索的语言的语言代码
     * 支持: cs, da, de, en, es, fr, id, it, hu, nl, no, pl, pt, ro, sk, fi, sv, tr, vi, th, bg, ru, el, ja, ko, zh
     * 默认: "en"
     *
     * image_type
     * 图片类型
     * 支持: "all", "photo", "illustration", "vector"
     * 默认: "all"
     *
     * orientation
     * 方向
     * 支持: "all", "horizontal", "vertical"
     * 默认: "all"
     *
     * category
     * 分类
     * 支持: backgrounds, fashion, nature, science, education, feelings, health, people, religion, places, animals, industry,
     * computer, food, sports, transportation, travel, buildings, business, music
     *
     * min_width
     * 最小宽度
     * 默认: "0"
     *
     * min_height
     * 最小高度
     * 默认: "0"
     *
     * colors
     * 过滤图像的颜色属性，多个值用逗号分隔
     * 支持: "grayscale", "transparent", "red", "orange", "yellow", "green", "turquoise", "blue", "lilac", "pink", "white", "gray", "black", "brown"
     *
     * editors_choice
     * 小编精选
     * 支持: "true", "false"
     * 默认: "false"
     *
     * safesearch
     * 只返回适合所有年龄的图像
     * 支持: "true", "false"
     * 默认: "false"
     *
     * order
     * 排序
     * 支持: "popular", "latest"
     * 默认: "popular"
     *
     * page
     * 页码
     * 默认: 1
     *
     * per_page
     * 每页返回数量
     * 支持: 3 - 200
     * 默认: 20
     */
    private fun newRequestUrl(map: Map<String, String?>?): String {
        val key = if (map?.containsKey(PAGE) == true && map[PAGE] == "1") {
            queryKeys.random().also { queryKey = it }
        } else {
            queryKey
        }
        L.vv(key)
        val sb = StringBuilder(BASE_URL)
        sb.append("?key=").append(PIXABAY_KEY)
        sb.append("&lang=zh")
        sb.append("&q=${key}")
        sb.append("&per_page=${PAGE_SIZE}")
//        sb.append("&category=nature")
//        sb.append("&min_width=2560")
//        sb.append("&min_height=1440")
        map?.forEach { sb.append("&${it.key}=${it.value}") }
        return sb.toString()
    }

    private var queryKey: String? = null
    private val queryKeys = arrayOf(
        "backgrounds",
        "fashion",
        "nature",
        "science",
        "education",
        "feelings",
        "health",
        "people",
        "religion",
        "places",
        "animals",
        "industry",
        "computer",
        "food",
        "sports",
        "transportation",
        "travel",
        "buildings",
        "business",
        "music"
    )
}