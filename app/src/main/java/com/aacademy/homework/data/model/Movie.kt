package com.aacademy.homework.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aacademy.homework.data.local.dao.Converters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
@TypeConverters(Converters::class)
data class Movie(
    @PrimaryKey val id: Long,
    val title: String,
    val poster: String,
    val backdrop: String,
    val ageLimit: Int,
    val rating: Float,
    val reviews: Int,
    val runtime: Int,
    val genres: List<Genre>,
    val overview: String,
    val popularity: Float,
    var isLiked: Boolean = false
) : Parcelable
