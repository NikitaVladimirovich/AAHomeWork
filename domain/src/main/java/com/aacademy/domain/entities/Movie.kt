package com.aacademy.domain.entities

data class Movie(
    val id: Long,
    val title: String,
    val poster: String,
    val backdrop: String,
    val ageLimit: Int,
    val rating: Float,
    val reviews: Int,
    val runtime: Int,
    val genres: List<Genre>,
    val overview: String,
    val popularity: Float,
    var isLiked: Boolean = false,
)
