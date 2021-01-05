package com.aacademy.homework.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JsonMovie(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String?,
    @SerialName("backdrop_path")
    val backdropPicture: String?,
    @SerialName("genre_ids")
    val genreIds: List<Long>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("overview")
    val overview: String,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("vote_count")
    val voteCount: Int
)
