package com.aacademy.homework.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "actorId"])
data class MovieActor(
    val movieId: Int,
    val actorId: Int
)
