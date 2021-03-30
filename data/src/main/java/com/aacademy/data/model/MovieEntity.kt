package com.aacademy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aacademy.data.local.dao.Converters

@Entity
@TypeConverters(Converters::class)
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val poster: String,
    val backdrop: String,
    val ageLimit: Int,
    val rating: Float,
    val reviews: Int,
    val runtime: Int,
    val genreEntities: List<GenreEntity>,
    val overview: String,
    val popularity: Float,
    var isLiked: Boolean = false
)
