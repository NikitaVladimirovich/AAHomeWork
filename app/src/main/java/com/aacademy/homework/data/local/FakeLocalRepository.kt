package com.aacademy.homework.data.local

import androidx.room.Room
import com.aacademy.homework.MyApp
import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

object FakeLocalRepository {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(MyApp.INSTANCE, AppDatabase::class.java, "sqlite.db")
            .fallbackToDestructiveMigration()
//            .createFromAsset("sqlite.db")
            .build()
    }

    suspend fun getAllMoviePreviews(): List<MoviePreviewWithGenres> {
        return database.moviePreviewDao().getAllMovies()
    }

    suspend fun getMovieDetail(id: Long): List<MovieDetailWithActors> {
        return database.movieDetailDao().getMovieDetail(id)
    }

    suspend fun setMovieLiked(id: Long, isLiked: Boolean) {
        return database.moviePreviewDao().setMovieLiked(id, isLiked)
    }

    suspend fun cacheMoviePreviewsWithTags(moviePreviewsWithTags: List<MoviePreviewWithGenres>) {
        database.moviePreviewDao().insert(moviePreviewsWithTags)
    }

    suspend fun cacheMovieDetailWithActors(movieDetailWithActors: MovieDetailWithActors) {
        database.movieDetailDao().insert(movieDetailWithActors)
    }
}
