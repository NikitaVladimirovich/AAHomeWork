package com.aacademy.homework

import android.app.Application
import android.os.StrictMode
import timber.log.Timber

class MyApp : Application() {

    companion object {

        lateinit var INSTANCE: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(Timber.DebugTree())
        }
    }
}
