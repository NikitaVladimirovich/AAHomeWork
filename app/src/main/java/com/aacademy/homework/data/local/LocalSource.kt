package com.aacademy.homework.data.local

import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

interface LocalSource {

    suspend fun getAllMoviePreviews(): List<MoviePreviewWithGenres>

    suspend fun getMovieDetail(id: Long): List<MovieDetailWithActors>

    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    suspend fun cacheMoviePreviewsWithGenres(moviePreviewsWithTags: List<MoviePreviewWithGenres>)

    suspend fun cacheMovieDetailWithActors(movieDetailWithActors: MovieDetailWithActors)
}
