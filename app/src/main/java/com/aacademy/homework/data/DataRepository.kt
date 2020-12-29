package com.aacademy.homework.data

import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

interface DataRepository {

    suspend fun getAllPreviews(): List<MoviePreviewWithGenres>

    suspend fun loadAllPreviews(): List<MoviePreviewWithGenres>

    suspend fun getMovieDetail(id: Long): MovieDetailWithActors

    suspend fun setMovieLiked(id: Long, isLiked: Boolean)
}
