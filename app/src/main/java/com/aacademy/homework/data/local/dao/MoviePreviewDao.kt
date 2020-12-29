package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.homework.data.model.MoviePreview

@Dao
interface MoviePreviewDao {

    @Transaction
    @Query("SELECT * FROM moviepreview")
    suspend fun getAllMovies(): List<MoviePreview>

    @Query("UPDATE moviepreview SET isLiked = :isLiked WHERE id = :id")
    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    @Transaction
    suspend fun insert(moviePreviewsWithTags: List<MoviePreview>) {
        for (moviePreview in moviePreviewsWithTags) {
            insert(moviePreview)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moviePreview: MoviePreview)

    @Query("DELETE FROM moviepreview")
    suspend fun clearMoviesPreviews()
}
