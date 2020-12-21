package com.aacademy.homework

import android.app.Application
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
import androidx.preference.PreferenceManager
import timber.log.Timber

class MyApp : Application() {

    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }

    companion object {

        lateinit var INSTANCE: MyApp
        const val THEME = "THEME"
    }

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(prefs.getInt(THEME, MODE_NIGHT_UNSPECIFIED))
        super.onCreate()
        INSTANCE = this

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(Timber.DebugTree())
        }
    }
}
