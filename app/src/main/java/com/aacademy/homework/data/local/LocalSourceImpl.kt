package com.aacademy.homework.data.local

import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(private val database: AppDatabase) : LocalSource {

    override suspend fun getAllMoviePreviews(): List<MoviePreviewWithGenres> =
        database.moviePreviewDao().getAllMovies()

    override suspend fun getMovieDetail(id: Long): List<MovieDetailWithActors> =
        database.movieDetailDao().getMovieDetail(id)

    override suspend fun setMovieLiked(id: Long, isLiked: Boolean) =
        database.moviePreviewDao().setMovieLiked(id, isLiked)

    override suspend fun cacheMoviePreviewsWithGenres(moviePreviewsWithGenres: List<MoviePreviewWithGenres>) {
        database.moviePreviewDao().clear()
        database.moviePreviewDao().insert(moviePreviewsWithGenres)
    }

    override suspend fun cacheMovieDetailWithActors(movieDetailWithActors: MovieDetailWithActors) =
        database.movieDetailDao().insert(movieDetailWithActors)
}
