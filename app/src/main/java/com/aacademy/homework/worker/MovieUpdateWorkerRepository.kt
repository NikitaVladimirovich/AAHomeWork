package com.aacademy.homework.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import com.aacademy.homework.BuildConfig
import java.util.concurrent.TimeUnit

object MovieUpdateWorkerRepository {

    const val WORK_NAME = "${BuildConfig.APPLICATION_ID}.MovieUpdateWorker"

    private val constraints =
        Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    val request = PeriodicWorkRequestBuilder<MovieUpdateWorker>(8, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()
}
