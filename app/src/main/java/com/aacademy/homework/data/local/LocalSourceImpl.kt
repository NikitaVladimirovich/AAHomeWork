package com.aacademy.homework.data.local

import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MoviePreview
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(private val database: AppDatabase) : LocalSource {

    override suspend fun getAllMoviePreviews(): List<MoviePreview> =
        database.moviePreviewDao().getAllMovies()

    override suspend fun getMovieDetail(id: Long): List<MovieDetail> =
        database.movieDetailDao().getMovieDetail(id)

    override suspend fun setMovieLiked(id: Long, isLiked: Boolean) =
        database.moviePreviewDao().setMovieLiked(id, isLiked)

    override suspend fun cacheMoviePreviews(moviePreviews: List<MoviePreview>) {
        database.moviePreviewDao().clearMoviesPreviews()
        database.moviePreviewDao().insert(moviePreviews)
    }

    override suspend fun cacheMovieDetail(movieDetail: MovieDetail) =
        database.movieDetailDao().insert(movieDetail)
}
