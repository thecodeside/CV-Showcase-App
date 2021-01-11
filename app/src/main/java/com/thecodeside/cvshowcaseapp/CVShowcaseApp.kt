package com.thecodeside.cvshowcaseapp

import android.app.Application
import com.thecodeside.cvshowcaseapp.common.utils.TimberInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CVShowcaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        TimberInitializer.init()
    }
}
