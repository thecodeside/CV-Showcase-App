package com.thecodeside.cvshowcaseapp.common.utils

import timber.log.Timber


object TimberInitializer {

    fun init() {
        Timber.plant(Timber.DebugTree())
    }
}