package com.aacademy.homework.data.local

import com.aacademy.homework.data.model.Movie

interface LocalSource {

    suspend fun getPopularMovies(): List<Movie>

    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    suspend fun cachePopularMovies(movies: List<Movie>)
}
