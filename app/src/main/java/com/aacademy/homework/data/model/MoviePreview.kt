package com.aacademy.homework.data.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class MoviePreview(
    @PrimaryKey val id: Int,
    val title: String,
    val coverPath: String,
    val ageLimit: Int,
    val rating: Int,
    val reviews: Int,
    val min: Int,
    var isLiked: Boolean = false,
    @Ignore val tags: List<Tag>
) : Parcelable {

    // Need to declare that constructor for Room
    constructor(
        id: Int,
        title: String,
        coverPath: String,
        ageLimit: Int,
        rating: Int,
        reviews: Int,
        min: Int,
        isLiked: Boolean
    ) : this(id, title, coverPath, ageLimit, rating, reviews, min, isLiked, emptyList())

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(coverPath)
        parcel.writeInt(ageLimit)
        parcel.writeInt(rating)
        parcel.writeInt(reviews)
        parcel.writeInt(min)
        parcel.writeByte(if (isLiked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<MoviePreview> {

        override fun createFromParcel(parcel: Parcel): MoviePreview {
            return MoviePreview(parcel)
        }

        override fun newArray(size: Int): Array<MoviePreview?> {
            return arrayOfNulls(size)
        }
    }
}
