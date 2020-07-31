package com.junpu.pixabaygallery.net

/**
 * 加载状态
 * @author junpu
 * @date 2020/7/31
 */
enum class LoadStatus(val msg: String) {
    LOADING_INITIAL("加载中"),
    LOADING("加载中"),
    SUCCESS("加载完成"),
    FAILURE("加载失败，点击重试"),
    COMPLETE("全部加载完成")
}