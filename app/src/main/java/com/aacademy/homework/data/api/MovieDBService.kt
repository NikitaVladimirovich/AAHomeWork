package com.aacademy.homework.data.api

import com.aacademy.homework.data.api.model.ActorsResponse
import com.aacademy.homework.data.api.model.Configuration
import com.aacademy.homework.data.api.model.GenresResponse
import com.aacademy.homework.data.api.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBService {

    @GET("configuration")
    suspend fun getConfiguration(): Configuration

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("sort_by") sort: String = "popularity.desc"
    ): MoviesResponse

    @GET("search/movie")
    suspend fun getMovies(@Query("query") query: String, @Query("page") page: Int): MoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(
        @Path("movie_id") movieId: Long
    ): ActorsResponse
}
