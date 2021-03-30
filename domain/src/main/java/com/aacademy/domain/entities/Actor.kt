package com.aacademy.domain.entities

data class Actor(
    val id: Long,
    val name: String,
    val profilePath: String?,
    var movieId: Long = 0
)
