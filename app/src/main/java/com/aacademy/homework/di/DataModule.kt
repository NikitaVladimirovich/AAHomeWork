package com.aacademy.homework.di

import android.content.Context
import androidx.room.Room
import com.aacademy.homework.BuildConfig
import com.aacademy.homework.data.api.MoviesService
import com.aacademy.homework.data.local.dao.AppDatabase
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Converter
import retrofit2.Retrofit

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "sqlite.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMoviesService(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): MoviesService {
        return Retrofit.Builder().baseUrl("https://gist.githubusercontent.com/NikitaVladimirovich/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
            .create(MoviesService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply { addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE)) }
            .build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
    }
}
