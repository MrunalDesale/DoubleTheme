package com.demo.listdarktheme.application

import android.app.Application
import android.content.Context

class ThemeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        lateinit var appInstance: ThemeApplication

        val appContext: Context by lazy {
            appInstance.applicationContext
        }
    }
}