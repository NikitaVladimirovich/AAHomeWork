package com.aacademy.homework.data.api

import com.aacademy.homework.data.api.model.JsonMovie
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre

interface ApiSource {

    suspend fun getMovies(): List<JsonMovie>

    suspend fun getGenres(): List<Genre>

    suspend fun getActors(): List<Actor>
}
