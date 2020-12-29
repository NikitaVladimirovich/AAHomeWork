package com.aacademy.homework.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity
data class Genre(
    @PrimaryKey val id: Long,
    val name: String
) : Parcelable
