package com.aacademy.data.api

import com.aacademy.data.api.model.ActorsResponse
import com.aacademy.data.api.model.Configuration
import com.aacademy.data.api.model.GenresResponse
import com.aacademy.data.api.model.MoviesResponse
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class ApiSourceImpl @Inject constructor(private val moviesService: MovieDBService) : ApiSource {

    override suspend fun getConfiguration(): Configuration = moviesService.getConfiguration()

    override suspend fun getPopularMovies(page: Int): MoviesResponse = moviesService.getPopularMovies(page)

    override suspend fun getMovies(query: String, page: Int): MoviesResponse = moviesService.getMovies(query, page)

    override suspend fun getGenres(): GenresResponse = moviesService.getGenres()

    override suspend fun getActors(movieId: Long): ActorsResponse = moviesService.getActors(movieId)
}
