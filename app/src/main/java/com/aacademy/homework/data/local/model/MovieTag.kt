package com.aacademy.homework.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "tagId"])
data class MovieTag(
    var movieId: Int,
    var tagId: Int
)
