package com.aacademy.homework.data.local

import androidx.room.Room
import com.aacademy.homework.MyApp
import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithTags
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

object FakeLocalRepository {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(MyApp.INSTANCE, AppDatabase::class.java, "sqlite.db")
//            .createFromAsset("sqlite.db")
            .build()
    }

    fun getAllMoviePreviews(): Single<List<MoviePreviewWithTags>> {
        return database.moviePreviewDao().getAllMovies()
    }

    fun getMovieDetail(id: Int): Single<List<MovieDetailWithActors>> {
        return database.movieDetailDao().getMovieDetail(id)
    }

    fun setMovieLiked(id: Int, isLiked: Boolean): Completable {
        return database.moviePreviewDao().setMovieLiked(id, isLiked)
    }

    fun cacheMoviePreviewsWithTags(moviePreviewsWithTags: List<MoviePreviewWithTags>) {
        database.moviePreviewDao().insert(moviePreviewsWithTags)
    }

    fun cacheMovieDetailWithActors(movieDetailWithActors: MovieDetailWithActors) {
        database.movieDetailDao().insert(movieDetailWithActors)
    }
}
