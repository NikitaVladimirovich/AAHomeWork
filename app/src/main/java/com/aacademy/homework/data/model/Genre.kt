package com.aacademy.homework.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Genre(
    val id: Long,
    val name: String
) : Parcelable
