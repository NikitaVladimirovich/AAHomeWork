package com.aacademy.homework.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActorParcelable(
    val id: Long,
    val name: String,
    val profilePath: String?,
    var movieId: Long = 0
) : Parcelable
