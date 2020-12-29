package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.MovieGenre
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MoviePreviewWithGenres

@Dao
interface MoviePreviewDao {

    @Transaction
    @Query("SELECT * FROM moviepreview")
    suspend fun getAllMovies(): List<MoviePreviewWithGenres>

    @Query("UPDATE moviepreview SET isLiked = :isLiked WHERE id = :id")
    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    @Transaction
    suspend fun insert(moviePreviewsWithTags: List<MoviePreviewWithGenres>) {
        for (moviePreview in moviePreviewsWithTags) {
            insert(moviePreview.moviePreview)
            for (tag in moviePreview.genres) {
                insert(tag)
                insert(MovieGenre(moviePreview.moviePreview.id, tag.id))
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moviePreview: MoviePreview)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieGenre: MovieGenre)

    @Transaction
    suspend fun clear() {
        clearMoviesPreviews()
        clearGenres()
        clearMoviesGenres()
    }

    @Query("DELETE FROM moviepreview")
    suspend fun clearMoviesPreviews()

    @Query("DELETE FROM genre")
    suspend fun clearGenres()

    @Query("DELETE FROM moviegenre")
    suspend fun clearMoviesGenres()
}
