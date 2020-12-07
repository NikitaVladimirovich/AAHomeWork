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
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDetailDao {

    @Transaction
    @Query("SELECT * FROM moviedetail WHERE id = :id")
    fun getMovieDetail(id: Int): Single<List<MovieDetailWithActors>>

    @Transaction
    @Insert
    fun insert(movieDetailWithActors: MovieDetailWithActors) {
        insert(movieDetailWithActors.movieDetail)
        for (actor in movieDetailWithActors.cast) {
            insert(actor)
            insert(MovieActor(movieDetailWithActors.movieDetail.id, actor.id))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieDetail: MovieDetail): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(actor: Actor): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieActor: MovieActor): Long
}