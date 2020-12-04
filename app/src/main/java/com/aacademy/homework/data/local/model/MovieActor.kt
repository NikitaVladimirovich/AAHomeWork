package com.aacademy.homework.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "actorId"])
data class MovieActor(
    var movieId: Int,
    var actorId: Int
)
