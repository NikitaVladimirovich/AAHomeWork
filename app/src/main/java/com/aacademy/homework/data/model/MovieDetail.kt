package com.aacademy.homework.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class MovieDetail(
    @PrimaryKey val id: Int,
    val storyline: String,
    @Ignore val cast: List<Actor>
) {

    constructor(id: Int, storyline: String) : this(id, storyline, emptyList())
}