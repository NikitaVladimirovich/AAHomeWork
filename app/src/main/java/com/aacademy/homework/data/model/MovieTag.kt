package com.aacademy.homework.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "tagId"])
data class MovieTag(
    val movieId: Int,
    val tagId: Int
)
