package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aacademy.homework.data.model.MovieDetail

@Dao
interface MovieDetailDao {

    @Transaction
    @Query("SELECT * FROM moviedetail WHERE id = :id")
    suspend fun getMovieDetail(id: Long): List<MovieDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetailWithActors: MovieDetail)
}
