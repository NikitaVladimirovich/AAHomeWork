package com.aacademy.homework.data.local.model

data class Movie(
    val id: Int,
    val title: String,
    val coverPath: String,
    val ageLimit: Int,
    val tags: List<String>,
    val rating: Int,
    val reviews: Int,
    val storyline: String,
    val cast: List<Actor>
)