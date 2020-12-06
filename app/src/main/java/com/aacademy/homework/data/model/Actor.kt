package com.aacademy.homework.data.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Actor(
    @PrimaryKey val id: Int,
    val firstName: String,
    val lastName: String,
    val photoPath: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(photoPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Actor> {

        override fun createFromParcel(parcel: Parcel): Actor {
            return Actor(parcel)
        }

        override fun newArray(size: Int): Array<Actor?> {
            return arrayOfNulls(size)
        }
    }
}