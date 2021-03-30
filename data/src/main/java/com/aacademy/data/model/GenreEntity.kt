package com.aacademy.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)
