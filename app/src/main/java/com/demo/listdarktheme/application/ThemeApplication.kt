package com.demo.listdarktheme.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThemeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}