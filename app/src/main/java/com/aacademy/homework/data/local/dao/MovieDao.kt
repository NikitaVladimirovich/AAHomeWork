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
    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    suspend fun getAllMovies(): List<Movie>

    @Query("UPDATE movie SET isLiked = :isLiked WHERE id = :id")
    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    @Transaction
    suspend fun update(movies: List<Movie>) {
        var oldMovies = getAllMovies()
        val moviesIds = movies.map { it.id }
        val moviesForDelete = oldMovies.filter { !moviesIds.contains(it.id) }
        oldMovies = oldMovies.filter { moviesIds.contains(it.id) }
        moviesForDelete.forEach { deleteOldMovie(it.id) }
        for (movie in movies) {
            oldMovies.firstOrNull { it.id == movie.id }?.let {
                movie.isLiked = it.isLiked
            }
            insert(movie)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movie WHERE id != :movieId")
    suspend fun deleteOldMovie(movieId: Long)
}
