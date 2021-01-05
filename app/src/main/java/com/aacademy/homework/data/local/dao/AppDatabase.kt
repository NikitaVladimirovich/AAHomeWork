package com.aacademy.homework.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MoviePreview

@Database(
    entities = [MoviePreview::class, MovieDetail::class],
    exportSchema = false,
    version = 4
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviePreviewDao(): MoviePreviewDao
    abstract fun movieDetailDao(): MovieDetailDao
}
