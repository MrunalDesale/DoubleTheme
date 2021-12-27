package com.demo.listdarktheme.utils

import android.content.Context

private const val prefName: String = "ThemePreference"
private const val themeType = "ThemeType"

fun getThemeType(appContext: Context): String? {
    val sharedPref = appContext.getSharedPreferences(prefName, 0)
    return sharedPref.getString(themeType, null)
}

fun setThemeType(appContext: Context, deviceId: String) {
    appContext.getSharedPreferences(prefName, 0).edit().apply {
        putString(themeType, deviceId)
    }.apply()
}