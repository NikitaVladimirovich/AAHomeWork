package com.aacademy.data.local.dao

import androidx.room.TypeConverter
import com.aacademy.data.model.GenreEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun genresToString(genreEntities: List<GenreEntity>): String = Json.encodeToString(genreEntities)

    @TypeConverter
    fun stringToGenre(genres: String): List<GenreEntity> = Json.decodeFromString(genres)
}
