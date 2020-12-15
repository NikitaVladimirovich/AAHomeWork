package com.aacademy.homework.data.local

import androidx.room.Room
import com.aacademy.homework.MyApp
import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

object LocalSourceImpl : LocalSource {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(MyApp.INSTANCE, AppDatabase::class.java, "sqlite.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    override suspend fun getAllMoviePreviews(): List<MoviePreviewWithGenres> {
        return database.moviePreviewDao().getAllMovies()
    }

    override suspend fun getMovieDetail(id: Long): List<MovieDetailWithActors> {
        return database.movieDetailDao().getMovieDetail(id)
    }

    override suspend fun setMovieLiked(id: Long, isLiked: Boolean) {
        return database.moviePreviewDao().setMovieLiked(id, isLiked)
    }

    override suspend fun cacheMoviePreviewsWithGenres(moviePreviewsWithTags: List<MoviePreviewWithGenres>) {
        database.moviePreviewDao().insert(moviePreviewsWithTags)
    }

    override suspend fun cacheMovieDetailWithActors(movieDetailWithActors: MovieDetailWithActors) {
        database.movieDetailDao().insert(movieDetailWithActors)
    }
}
