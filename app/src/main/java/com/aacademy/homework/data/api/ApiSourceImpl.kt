package com.aacademy.homework.data.api

import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.JsonMovie
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit

@ExperimentalSerializationApi
object ApiSourceImpl : ApiSource {

    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private val okHttpClient =
        OkHttpClient.Builder().apply { addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY)) }.build()
    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl("https://gist.githubusercontent.com/NikitaVladimirovich/")
            .addConverterFactory(jsonFormat.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

    private val api: ApiSource = retrofit.create(ApiSource::class.java)

    override suspend fun getMovies(): List<JsonMovie> = api.getMovies()

    override suspend fun getGenres(): List<Genre> = api.getGenres()

    override suspend fun getActors(): List<Actor> = api.getActors()
}
