package com.aacademy.homework.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity
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
    val cast: List<Actor>,
)