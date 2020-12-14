package com.aacademy.homework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetail(
    @PrimaryKey val id: Long,
    val overview: String
)
