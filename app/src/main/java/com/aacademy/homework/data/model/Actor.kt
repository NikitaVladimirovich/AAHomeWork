package com.aacademy.homework.data.model

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(primaryKeys = ["id", "movieId"])
@Serializable
data class Actor(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?,
    var movieId: Long = 0
)
