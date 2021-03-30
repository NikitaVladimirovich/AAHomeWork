package com.aacademy.data.api.model

import com.aacademy.data.model.GenreEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("genres")
    val genreEntities: List<GenreEntity>
)
