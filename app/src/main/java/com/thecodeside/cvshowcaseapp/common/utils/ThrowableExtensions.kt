package com.thecodeside.cvshowcaseapp.common.utils

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException


fun Throwable?.isNetworkFailure(): Boolean {
    return this is SocketTimeoutException ||
            this is UnknownHostException
}

fun Throwable?.guardCancellation() {
    if (this is CancellationException) {
        throw this
    }
}
