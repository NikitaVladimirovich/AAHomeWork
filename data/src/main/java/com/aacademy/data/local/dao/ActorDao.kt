package com.aacademy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.data.model.ActorEntity

@Dao
interface ActorDao {

    @Transaction
    @Query("SELECT * FROM actorentity WHERE movieId = :movieId")
    suspend fun getActors(movieId: Long): List<ActorEntity>

    @Transaction
    suspend fun insert(actorEntities: List<ActorEntity>) {
        for (actor in actorEntities) {
            insert(actor)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actorEntity: ActorEntity)

    @Query("DELETE FROM actorentity WHERE movieId == :movieId")
    suspend fun clearActors(movieId: Long)
}
