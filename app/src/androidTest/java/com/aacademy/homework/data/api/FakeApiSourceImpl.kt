package com.aacademy.homework.data.api

import androidx.test.platform.app.InstrumentationRegistry
import com.aacademy.homework.data.api.model.ActorsResponse
import com.aacademy.homework.data.api.model.Configuration
import com.aacademy.homework.data.api.model.GenresResponse
import com.aacademy.homework.data.api.model.MoviesResponse
import javax.inject.Inject

class FakeApiSourceImpl @Inject constructor() : ApiSource {

    private val context = InstrumentationRegistry.getInstrumentation().context

    override suspend fun getConfiguration(): Configuration = loadConfiguration(context)

    override suspend fun getPopularMovies(page: Int): MoviesResponse =
        MoviesResponse(1, loadJsonMovies(context), 1, 1)

    override suspend fun getGenres(): GenresResponse = GenresResponse(loadGenres(context))

    override suspend fun getMovies(query: String, page: Int): MoviesResponse =
        MoviesResponse(1, loadJsonMovies(context), 1, 1)

    override suspend fun getActors(movieId: Long): ActorsResponse =
        ActorsResponse(loadActors(context), movieId.toInt())
}
