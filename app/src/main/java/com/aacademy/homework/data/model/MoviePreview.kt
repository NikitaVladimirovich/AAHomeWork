package com.aacademy.homework.data.model

import androidx.room.Entity
import androidx.room.Ignore
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
    var isLiked: Boolean = false,
    @Ignore val tags: List<Tag>
) {

    // Need to declare that constructor for Room
    constructor(
        id: Int,
        title: String,
        coverPath: String,
        ageLimit: Int,
        rating: Int,
        reviews: Int,
        min: Int,
        isLiked: Boolean
    ) : this(id, title, coverPath, ageLimit, rating, reviews, min, isLiked, emptyList())
}
