package com.aacademy.data.api

import com.aacademy.data.api.model.ActorsResponse
import com.aacademy.data.api.model.Configuration
import com.aacademy.data.api.model.GenresResponse
import com.aacademy.data.api.model.MoviesResponse

interface ApiSource {

    suspend fun getConfiguration(): Configuration

    suspend fun getPopularMovies(page: Int): MoviesResponse

    suspend fun getGenres(): GenresResponse

    suspend fun getMovies(query: String, page: Int): MoviesResponse

    suspend fun getActors(
        movieId: Long
    ): ActorsResponse
}
