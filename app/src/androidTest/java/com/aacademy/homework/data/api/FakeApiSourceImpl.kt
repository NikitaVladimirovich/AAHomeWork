package com.aacademy.homework.data.api

import android.content.Context
import com.aacademy.homework.data.api.model.JsonMovie
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FakeApiSourceImpl @Inject constructor(@ApplicationContext val context: Context) : ApiSource {

    override suspend fun getMovies(): List<JsonMovie> = loadJsonMovies(context)

    override suspend fun getGenres(): List<Genre> = loadGenres(context)

    override suspend fun getActors(): List<Actor> = loadActors(context)
}
