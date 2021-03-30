package com.aacademy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.data.model.MovieEntity

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movieentity WHERE title LIKE :query ORDER BY popularity DESC LIMIT 20")
    suspend fun getMovies(query: String = ""): List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movieentity WHERE isLiked = 1")
    suspend fun getLikedMovies(): List<MovieEntity>

    @Query("UPDATE movieentity SET isLiked = :isLiked WHERE id = :id")
    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    @Transaction
    suspend fun update(movieEntities: List<MovieEntity>) {
        val moviesIds = movieEntities.map { it.id }
        val likedMovies = getLikedMovies().filter { moviesIds.contains(it.id) }
        for (movie in movieEntities) {
            likedMovies.firstOrNull { it.id == movie.id }?.let {
                movie.isLiked = it.isLiked
            }
            insert(movie)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Query("DELETE FROM movieentity WHERE id != :movieId")
    suspend fun deleteOldMovie(movieId: Long)
}
