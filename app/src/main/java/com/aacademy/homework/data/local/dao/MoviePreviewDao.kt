package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import com.aacademy.homework.data.model.MovieTag

@Dao
interface MoviePreviewDao {

    @Transaction
    @Query("SELECT * FROM moviepreview")
    suspend fun getAllMovies(): List<MoviePreviewWithGenres>

    @Query("UPDATE moviepreview SET isLiked = :isLiked WHERE id = :id")
    suspend fun setMovieLiked(id: Int, isLiked: Boolean)

    @Transaction
    suspend fun insert(moviePreviewsWithTags: List<MoviePreviewWithGenres>) {
        for (moviePreview in moviePreviewsWithTags) {
            insert(moviePreview.moviePreview)
            for (tag in moviePreview.genres) {
                insert(tag)
                insert(MovieTag(moviePreview.moviePreview.id, tag.id))
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moviePreview: MoviePreview): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: Genre): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieTag: MovieTag): Long
}