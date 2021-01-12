package com.aacademy.homework.data.api

import com.aacademy.homework.data.api.model.ActorsResponse
import com.aacademy.homework.data.api.model.Configuration
import com.aacademy.homework.data.api.model.GenresResponse
import com.aacademy.homework.data.api.model.MoviesResponse

interface ApiSource {

    suspend fun getConfiguration(): Configuration

    suspend fun getPopularMovies(page: Int): MoviesResponse

    suspend fun getGenres(): GenresResponse

//    @GET("movie/{movie_id}")
//    suspend fun getDetails(
//        @Path("movie_id") movieId: Int
//    ): DetailsResponse

    suspend fun getActors(
        movieId: Long
    ): ActorsResponse
}
