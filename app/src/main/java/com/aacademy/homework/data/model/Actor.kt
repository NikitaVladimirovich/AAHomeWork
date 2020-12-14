package com.aacademy.homework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Actor(
    @PrimaryKey val id: Long,
    val name: String,
    @SerialName("profile_path")
    val picture: String
)
