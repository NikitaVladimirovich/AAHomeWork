package com.aacademy.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aacademy.data.model.ActorEntity
import com.aacademy.data.model.MovieEntity

@Database(
    entities = [MovieEntity::class, ActorEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
}
