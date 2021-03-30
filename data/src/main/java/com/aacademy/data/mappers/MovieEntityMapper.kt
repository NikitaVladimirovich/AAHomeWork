package com.aacademy.data.mappers

import com.aacademy.data.model.MovieEntity
import com.aacademy.domain.entities.Movie

object MovieEntityMapper {

    fun toMovie(movieEntity: MovieEntity) = Movie(
        movieEntity.id,
        movieEntity.title,
        movieEntity.poster,
        movieEntity.backdrop,
        movieEntity.ageLimit,
        movieEntity.rating,
        movieEntity.reviews,
        movieEntity.runtime,
        movieEntity.genreEntities.map { GenreEntityMapper.toGenre(it) },
        movieEntity.overview,
        movieEntity.popularity,
        movieEntity.isLiked
    )
}
