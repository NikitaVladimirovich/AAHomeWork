package com.aacademy.homework.data.local.dao

import androidx.room.TypeConverter
import com.aacademy.homework.data.model.Genre
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun genresToString(genres: List<Genre>): String = Json.encodeToString(genres)

    @TypeConverter
    fun stringToGenre(genres: String): List<Genre> = Json.decodeFromString(genres)
}
