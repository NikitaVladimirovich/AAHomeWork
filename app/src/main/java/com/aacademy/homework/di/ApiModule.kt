package com.aacademy.homework.di

import android.content.Context
import com.aacademy.homework.BuildConfig
import com.aacademy.homework.data.api.ApiKeyQueryInterceptor
import com.aacademy.homework.data.api.MovieDBService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideMoviesDBService(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): MovieDBService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
            .create(MovieDBService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
//                cache(cache)
                addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE))
                addInterceptor(ApiKeyQueryInterceptor())
//                addNetworkInterceptor(NetworkInterceptor())
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 5 * 1024 * 1024.toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())
    }
}
