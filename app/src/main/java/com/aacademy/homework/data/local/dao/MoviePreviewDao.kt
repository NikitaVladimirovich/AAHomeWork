package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MoviePreviewWithTags
import com.aacademy.homework.data.model.MovieTag
import com.aacademy.homework.data.model.Tag
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface MoviePreviewDao {

    @Transaction
    @Query("SELECT * FROM moviepreview")
    fun getAllMovies(): Single<List<MoviePreviewWithTags>>

    @Query("UPDATE moviepreview SET isLiked = :isLiked WHERE id = :id")
    fun setMovieLiked(id: Int, isLiked: Boolean): Completable

    @Transaction
    fun insert(moviePreviewsWithTags: List<MoviePreviewWithTags>) {
        for (moviePreview in moviePreviewsWithTags) {
            insert(moviePreview.moviePreview)
            for (tag in moviePreview.tags) {
                insert(tag)
                insert(MovieTag(moviePreview.moviePreview.id, tag.id))
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(moviePreview: MoviePreview): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tag: Tag): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieTag: MovieTag): Long
}