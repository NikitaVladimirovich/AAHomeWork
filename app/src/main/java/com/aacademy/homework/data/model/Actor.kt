package com.aacademy.homework.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?
)
