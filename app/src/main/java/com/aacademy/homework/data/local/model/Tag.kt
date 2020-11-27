package com.aacademy.homework.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(
    @PrimaryKey val id: Int,
    val name: String
)
