package com.aacademy.homework.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MoviePreviewWithGenres(
    @Embedded val moviePreview: MoviePreview,
    @Relation(
        parentColumn = "id",
        entity = Genre::class,
        entityColumn = "id",
        associateBy = Junction(
            value = MovieGenre::class,
            parentColumn = "movieId",
            entityColumn = "genreId"
        )
    )
    val genres: List<Genre>
)
