package com.aacademy.homework.data.api

import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.JsonMovie
import retrofit2.http.GET

interface MoviesService {

    @GET("777ca483b635752a03f0deb19e6f7ac7/raw/b9f1ad3f88b0905d9e34a017cde41796d38cc64a/data.json")
    suspend fun getMovies(): List<JsonMovie>

    @GET("14a425acf910045e684366fb1030b557/raw/f2c9e5e0fc898c3b4a8ce33a0c9e03ba92c639e4/genres.json")
    suspend fun getGenres(): List<Genre>

    @GET("63b257452a0070d20d16ab9f3dc117c2/raw/b1db487d76e9e9a072690f96735ccc68ed7af54c/people.json")
    suspend fun getActors(): List<Actor>
}
