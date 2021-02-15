package com.aacademy.homework.di

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.aacademy.homework.foundations.notification.NewMovieNotifications
import com.aacademy.homework.foundations.notification.Notifications
import com.aacademy.homework.worker.MovieUpdateWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object MovieUpdateModule {

    @Singleton
    @Provides
    fun provideConstraints(): Constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true).build()

    @Singleton
    @Provides
    fun providePeriodicWorkRequest(constraints: Constraints): PeriodicWorkRequest =
        PeriodicWorkRequestBuilder<MovieUpdateWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

    @Singleton
    @Provides
    fun provideNotifications(
        @ApplicationContext context: Context
    ): Notifications = NewMovieNotifications(context)
}
