package com.aacademy.homework.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JsonMovie(
    val id: Long,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Long>,
    @SerialName("actors")
    val actorsIds: List<Long>,
    @SerialName("vote_average")
    val ratings: Float,
    val overview: String,
    val adult: Boolean,
    @SerialName("vote_count")
    val voteCount: Int
)
