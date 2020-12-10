package com.aacademy.homework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Actor(
    @PrimaryKey val id: Int,
    val name: String,
    val picture: String
)
