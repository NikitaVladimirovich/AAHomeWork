package com.aacademy.homework.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreParcelable(
    val id: Long,
    val name: String
) : Parcelable
