package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.homework.data.model.Movie

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<Movie>

    @Query("UPDATE movie SET isLiked = :isLiked WHERE id = :id")
    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    @Transaction
    suspend fun insert(movies: List<Movie>) {
        for (movie in movies) {
            insert(movie)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movie")
    suspend fun clearMovies()
}
