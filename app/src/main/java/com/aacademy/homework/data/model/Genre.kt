package com.aacademy.homework.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Genre(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : Parcelable
