package com.aacademy.homework.data

import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

interface DataRepository {

    suspend fun getAllPreviews(withCache: Boolean = true): List<MoviePreviewWithGenres>

    suspend fun getMovieDetail(id: Long): MovieDetailWithActors

    suspend fun setMovieLiked(id: Long, isLiked: Boolean)
}
