package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.homework.data.model.Actor

@Dao
interface ActorDao {

    @Transaction
    @Query("SELECT * FROM actor WHERE movieId = :movieId")
    suspend fun getActors(movieId: Long): List<Actor>

    @Transaction
    suspend fun insert(actors: List<Actor>) {
        for (actor in actors) {
            insert(actor)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actor: Actor)

    @Query("DELETE FROM actor")
    suspend fun clearActors()
}
