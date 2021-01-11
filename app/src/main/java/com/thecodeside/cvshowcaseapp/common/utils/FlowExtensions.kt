package com.thecodeside.cvshowcaseapp.common.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

private const val DEFAULT_CLICK_COUNT = 0
private const val DEFAULT_START_TIME = 0L

fun <T> Flow<T>.windowedCount(bufferCount: Int, bufferTimeMillis: Long): Flow<Int> {
    var clickCount = DEFAULT_CLICK_COUNT
    var startTime = DEFAULT_START_TIME
    return transform {
        val currentTime = System.currentTimeMillis()
        val diff = currentTime - startTime
        if (diff > bufferTimeMillis || clickCount >= bufferCount) {
            startTime = currentTime
            clickCount = 1
            emit(clickCount)
        } else {
            emit(++clickCount)
        }
    }
}
