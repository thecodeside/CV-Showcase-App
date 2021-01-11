package com.thecodeside.cvshowcaseapp.common.utils

import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable?.isNetworkFailure(): Boolean {
    return this is SocketTimeoutException ||
            this is UnknownHostException
}
