package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.aacademy.homework.data.local.model.MoviePreviewWithTags
import io.reactivex.Observable

@Dao
interface MoviePreviewDao {

    @Query("SELECT * FROM moviepreview")
    fun getAllMovies(): Observable<List<MoviePreviewWithTags>>
}