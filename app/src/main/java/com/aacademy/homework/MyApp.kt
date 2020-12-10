package com.aacademy.homework

import android.app.Application

class MyApp : Application() {

    companion object {

        lateinit var INSTANCE: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}
