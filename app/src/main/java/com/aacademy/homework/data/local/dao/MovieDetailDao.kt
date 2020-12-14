package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.MovieActor
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MovieDetailWithActors

@Dao
interface MovieDetailDao {

    @Transaction
    @Query("SELECT * FROM moviedetail WHERE id = :id")
    suspend fun getMovieDetail(id: Long): List<MovieDetailWithActors>

    @Transaction
    @Insert
    suspend fun insert(movieDetailWithActors: MovieDetailWithActors) {
        insert(movieDetailWithActors.movieDetail)
        for (actor in movieDetailWithActors.actors) {
            insert(actor)
            insert(MovieActor(movieDetailWithActors.movieDetail.id, actor.id))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetail: MovieDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actor: Actor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieActor: MovieActor)
}
