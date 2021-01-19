package com.aacademy.homework.data.local

import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.model.Movie
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(private val database: AppDatabase) : LocalSource {

    override suspend fun getPopularMovies(): List<Movie> =
        database.movieDao().getAllMovies()

    override suspend fun setMovieLiked(id: Long, isLiked: Boolean) =
        database.movieDao().setMovieLiked(id, isLiked)

    override suspend fun cachePopularMovies(movies: List<Movie>) {
        database.movieDao().clearMovies()
        database.movieDao().insert(movies)
    }
}
