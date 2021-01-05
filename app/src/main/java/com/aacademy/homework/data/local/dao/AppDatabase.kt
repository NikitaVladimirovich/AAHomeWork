package com.aacademy.homework.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aacademy.homework.data.model.Movie

@Database(
    entities = [Movie::class],
    exportSchema = false,
    version = 5
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviePreviewDao(): MovieDao
}
