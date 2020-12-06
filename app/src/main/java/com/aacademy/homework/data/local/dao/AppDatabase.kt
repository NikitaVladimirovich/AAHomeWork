package com.aacademy.homework.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.MovieActor
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MovieTag
import com.aacademy.homework.data.model.Tag

@Database(
    entities = [MoviePreview::class, Tag::class, MovieTag::class, MovieDetail::class, Actor::class, MovieActor::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MoviePreviewDao
    abstract fun movieDetailDao(): MovieDetailDao
}