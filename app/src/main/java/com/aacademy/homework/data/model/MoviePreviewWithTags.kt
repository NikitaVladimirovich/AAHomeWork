package com.aacademy.homework.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MoviePreviewWithTags(
    @Embedded val moviePreview: MoviePreview,
    @Relation(
        parentColumn = "id",
        entity = Tag::class,
        entityColumn = "id",
        associateBy = Junction(
            value = MovieTag::class,
            parentColumn = "movieId",
            entityColumn = "tagId"
        )
    )
    val tags: List<Tag>,
)