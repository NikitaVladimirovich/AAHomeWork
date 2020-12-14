package com.aacademy.homework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviePreview(
    @PrimaryKey val id: Long,
    val title: String,
    val poster: String,
    val backdrop: String,
    val ageLimit: Int,
    val rating: Float,
    val reviews: Int,
    val runtime: Int,
    var isLiked: Boolean = false
)
