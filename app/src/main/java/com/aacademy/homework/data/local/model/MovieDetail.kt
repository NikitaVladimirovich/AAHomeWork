package com.aacademy.homework.data.local.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val coverPath: String,
    val ageLimit: Int,
    val rating: Int,
    val reviews: Int,
    val min: Int,
    var isLiked: Boolean,
    val cast: List<Actor>
)