package com.aacademy.homework.data.api

import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.JsonMovie
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class ApiSourceImpl @Inject constructor(private val moviesService: MoviesService) : ApiSource {

    override suspend fun getMovies(): List<JsonMovie> = moviesService.getMovies()

    override suspend fun getGenres(): List<Genre> = moviesService.getGenres()

    override suspend fun getActors(): List<Actor> = moviesService.getActors()
}
