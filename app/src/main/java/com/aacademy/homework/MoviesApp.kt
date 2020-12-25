package com.aacademy.homework

import android.app.Application
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import com.aacademy.homework.data.preferences.MyPreference
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MoviesApp : Application() {

    @Inject
    lateinit var prefs: MyPreference

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(prefs.appTheme)
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(Timber.DebugTree())
        }
    }
}
