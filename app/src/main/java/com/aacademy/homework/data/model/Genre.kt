package com.aacademy.homework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Genre(
    @PrimaryKey val id: Long,
    val name: String
)
