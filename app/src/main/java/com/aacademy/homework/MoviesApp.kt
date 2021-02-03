package com.aacademy.homework

import android.app.Application
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.aacademy.homework.data.preferences.MyPreference
import com.aacademy.homework.worker.MovieUpdateWorkerRepository
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MoviesApp : Application(), Configuration.Provider {

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()

    @Inject
    lateinit var prefs: MyPreference

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(prefs.appTheme)
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(Timber.DebugTree())
        }

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            MovieUpdateWorkerRepository.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            MovieUpdateWorkerRepository.request
        )
    }
}
