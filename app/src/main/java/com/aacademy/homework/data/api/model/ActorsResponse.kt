package com.aacademy.homework.data.api.model

import com.aacademy.homework.data.model.Actor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorsResponse(
    @SerialName("cast")
    val cast: List<Actor>,
    @SerialName("id")
    val id: Int
)
