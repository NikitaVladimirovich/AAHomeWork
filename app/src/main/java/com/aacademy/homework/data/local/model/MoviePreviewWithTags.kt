package com.aacademy.homework.data.local.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity
data class MoviePreviewWithTags(
    @Embedded val moviePreview: MoviePreview,
    @Relation(
        parentColumn = "id",
        entity = Tag::class,
        entityColumn = "id",
        associateBy = Junction(
            value = MovieTag::class,
            parentColumn = "movieId",
            entityColumn = "tagId"
        )
    )
    val tags: List<Tag>,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(MoviePreview::class.java.classLoader)!!,
        parcel.createTypedArrayList(Tag)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(moviePreview, flags)
        parcel.writeTypedList(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<MoviePreviewWithTags> {

        override fun createFromParcel(parcel: Parcel): MoviePreviewWithTags {
            return MoviePreviewWithTags(parcel)
        }

        override fun newArray(size: Int): Array<MoviePreviewWithTags?> {
            return arrayOfNulls(size)
        }
    }
}