package com.aacademy.homework

import android.app.Application
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MoviesApp : Application() {

    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }

    companion object {

        const val THEME = "THEME"
    }

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(prefs.getInt(THEME, MODE_NIGHT_YES))
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(Timber.DebugTree())
        }
    }
}
