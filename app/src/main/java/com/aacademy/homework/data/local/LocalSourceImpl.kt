package com.aacademy.homework.data.local

import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.model.Movie
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(private val database: AppDatabase) : LocalSource {

    override suspend fun getAllMoviePreviews(): List<Movie> =
        database.moviePreviewDao().getAllMovies()

    override suspend fun setMovieLiked(id: Long, isLiked: Boolean) =
        database.moviePreviewDao().setMovieLiked(id, isLiked)

    override suspend fun cacheMoviePreviews(movies: List<Movie>) {
        database.moviePreviewDao().clearMoviesPreviews()
        database.moviePreviewDao().insert(movies)
    }
}
