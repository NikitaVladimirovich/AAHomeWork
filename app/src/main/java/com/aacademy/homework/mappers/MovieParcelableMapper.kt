package com.aacademy.homework.mappers

import com.aacademy.domain.entities.Movie
import com.aacademy.homework.entities.MovieParcelable

object MovieParcelableMapper {

    fun toMovieParcelable(movieEntity: Movie) = MovieParcelable(
        movieEntity.id,
        movieEntity.title,
        movieEntity.poster,
        movieEntity.backdrop,
        movieEntity.ageLimit,
        movieEntity.rating,
        movieEntity.reviews,
        movieEntity.runtime,
        movieEntity.genres.map { GenreParcelableMapper.toGenreParcelable(it) },
        movieEntity.overview,
        movieEntity.popularity,
        movieEntity.isLiked
    )
}
