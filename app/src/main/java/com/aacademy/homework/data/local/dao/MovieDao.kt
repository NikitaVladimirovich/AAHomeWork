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
    @Query("SELECT * FROM movie WHERE title LIKE :query ORDER BY popularity DESC LIMIT 20")
    suspend fun getMovies(query: String = ""): List<Movie>

    @Transaction
    @Query("SELECT * FROM movie WHERE isLiked = 1")
    suspend fun getLikedMovies(): List<Movie>

    @Query("UPDATE movie SET isLiked = :isLiked WHERE id = :id")
    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    @Transaction
    suspend fun update(movies: List<Movie>) {
        val moviesIds = movies.map { it.id }
        val likedMovies = getLikedMovies().filter { moviesIds.contains(it.id) }
        for (movie in movies) {
            likedMovies.firstOrNull { it.id == movie.id }?.let {
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
