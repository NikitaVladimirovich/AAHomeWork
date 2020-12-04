package com.aacademy.homework.data.local

import androidx.room.Room
import com.aacademy.homework.MyApp
import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.local.model.MovieDetailWithActors
import com.aacademy.homework.data.local.model.MoviePreviewWithTags
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object MockRepository {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(MyApp.INSTANCE, AppDatabase::class.java, "sqlite.db")
            .createFromAsset("sqlite.db")
            .build()
    }

    fun getAllMoviePreviews(): Observable<List<MoviePreviewWithTags>> {
        return database.movieDao().getAllMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMovieDetail(id: Int): Observable<MovieDetailWithActors> {
        return database.movieDetailDao().getMovieDetail(id)
            .subscribeOn(Schedulers.io())
    }
}
