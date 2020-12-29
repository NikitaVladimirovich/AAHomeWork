package com.aacademy.homework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aacademy.homework.data.local.dao.Converters

@Entity
@TypeConverters(Converters::class)
data class MovieDetail(
    @PrimaryKey val id: Long,
    val overview: String,
    val actors: List<Actor>
)
