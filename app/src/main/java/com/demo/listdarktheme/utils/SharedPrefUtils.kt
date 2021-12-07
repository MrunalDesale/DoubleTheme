package com.demo.listdarktheme.utils

import android.content.SharedPreferences
import com.demo.listdarktheme.application.ThemeApplication

class SharedPrefUtils {
    companion object {
        private const val prefName: String = "ThemePreference"
        private var sharedPref = ThemeApplication.appContext.getSharedPreferences(prefName, 0)
        private const val themeType = "ThemeType"
        private lateinit var editor: SharedPreferences.Editor

        fun getThemeType(): String? {
            return sharedPref.getString(themeType, null)
        }

        fun setThemeType(deviceId: String) {
            editor = sharedPref.edit()
            editor.putString(themeType, deviceId)
            editor.apply()
        }
    }
}