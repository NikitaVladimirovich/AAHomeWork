package com.aacademy.homework.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieDetailWithActors(
    @Embedded val movieDetail: MovieDetail,
    @Relation(
        parentColumn = "id",
        entity = Actor::class,
        entityColumn = "id",
        associateBy = Junction(
            value = MovieActor::class,
            parentColumn = "movieId",
            entityColumn = "actorId"
        )
    )
    val actors: List<Actor>,
)