package com.aacademy.homework.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieParcelable(
    val id: Long,
    val title: String,
    val poster: String,
    val backdrop: String,
    val ageLimit: Int,
    val rating: Float,
    val reviews: Int,
    val runtime: Int,
    val genres: List<GenreParcelable>,
    val overview: String,
    val popularity: Float,
    var isLiked: Boolean = false
) : Parcelable
