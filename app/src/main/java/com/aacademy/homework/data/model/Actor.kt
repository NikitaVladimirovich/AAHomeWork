package com.aacademy.homework.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Long,
    val name: String,
    @SerialName("profile_path")
    val picture: String
)
