package com.aacademy.homework.mappers

import com.aacademy.domain.entities.Genre
import com.aacademy.homework.entities.GenreParcelable

object GenreParcelableMapper {

    fun toGenreParcelable(genreEntity: Genre) = GenreParcelable(genreEntity.id, genreEntity.name)
}
