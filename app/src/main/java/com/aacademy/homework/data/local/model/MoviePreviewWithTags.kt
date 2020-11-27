package com.aacademy.homework.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity
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
    var tags: List<Tag>,
)