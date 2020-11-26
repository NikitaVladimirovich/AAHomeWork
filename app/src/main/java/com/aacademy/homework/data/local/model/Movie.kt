package com.aacademy.homework.data.local.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

data class Movie(
    val id: Int,
    val title: String,
    val coverPath: String,
    val ageLimit: Int,
    val tags: List<String>,
    val rating: Int,
    val reviews: Int,
    val storyline: String,
    val min: Int,
    var isLiked: Boolean,
    val cast: List<Actor>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.createStringArrayList()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt() != 0,
        parcel.createTypedArrayList(Actor)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(coverPath)
        parcel.writeInt(ageLimit)
        parcel.writeStringList(tags)
        parcel.writeInt(rating)
        parcel.writeInt(reviews)
        parcel.writeString(storyline)
        parcel.writeInt(min)
        parcel.writeInt(if (isLiked) 1 else 0)
        parcel.writeTypedList(cast)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Movie> {

        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}