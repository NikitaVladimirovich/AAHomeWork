package com.aacademy.data.api.model

import com.aacademy.data.model.ActorEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorsResponse(
    @SerialName("cast")
    val cast: List<ActorEntity>,
    @SerialName("id")
    val id: Int
)
