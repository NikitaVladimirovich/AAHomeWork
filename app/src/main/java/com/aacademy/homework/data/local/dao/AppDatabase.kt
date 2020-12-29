package com.aacademy.homework.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.MovieActor
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MovieGenre
import com.aacademy.homework.data.model.MoviePreview

@Database(
    entities = [MoviePreview::class, Genre::class, MovieGenre::class, MovieDetail::class, Actor::class, MovieActor::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviePreviewDao(): MoviePreviewDao
    abstract fun movieDetailDao(): MovieDetailDao
}
