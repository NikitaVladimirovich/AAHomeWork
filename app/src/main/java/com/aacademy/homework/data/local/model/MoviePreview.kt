package com.aacademy.homework.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviePreview(
    @PrimaryKey val id: Int,
    val title: String,
    val coverPath: String,
    val ageLimit: Int,
    val rating: Int,
    val reviews: Int,
    val min: Int,
    var isLiked: Boolean,
) {
}