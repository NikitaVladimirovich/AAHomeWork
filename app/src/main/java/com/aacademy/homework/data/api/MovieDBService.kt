package com.aacademy.homework.data.api

import com.aacademy.homework.data.api.model.ActorsResponse
import com.aacademy.homework.data.api.model.Configuration
import com.aacademy.homework.data.api.model.GenresResponse
import com.aacademy.homework.data.api.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDBService {

    @GET("configuration")
    suspend fun getConfiguration(): Configuration

    @GET("movie/popular")
    suspend fun getPopularMovies(): MoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

//    @GET("movie/{movie_id}")
//    suspend fun getDetails(
//        @Path("movie_id") movieId: Int
//    ): DetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(
        @Path("movie_id") movieId: Long
    ): ActorsResponse
}
