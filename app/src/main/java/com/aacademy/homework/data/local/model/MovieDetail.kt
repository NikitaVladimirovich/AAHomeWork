package com.aacademy.homework.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetail(
    @PrimaryKey val id: Int,
    val storyline: String,
)