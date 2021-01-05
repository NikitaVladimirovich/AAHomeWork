package com.aacademy.homework.data.local

import com.aacademy.homework.data.model.Movie

interface LocalSource {

    suspend fun getAllMoviePreviews(): List<Movie>

    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    suspend fun cacheMoviePreviews(movies: List<Movie>)
}
