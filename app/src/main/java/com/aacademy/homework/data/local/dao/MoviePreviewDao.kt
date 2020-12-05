package com.aacademy.homework.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.aacademy.homework.data.local.model.MoviePreviewWithTags
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface MoviePreviewDao {

    @Query("SELECT * FROM moviepreview")
    fun getAllMovies(): Single<List<MoviePreviewWithTags>>

    @Query("UPDATE moviepreview SET isLiked = :isLiked WHERE id = :id")
    fun setMovieLiked(id: Int, isLiked: Boolean): Completable
}