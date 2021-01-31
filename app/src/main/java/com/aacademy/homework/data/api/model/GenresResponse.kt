package com.aacademy.homework.data.api.model

import com.aacademy.homework.data.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("genres")
    val genres: List<Genre>
)
