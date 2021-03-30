package com.aacademy.data.mappers

import com.aacademy.data.model.GenreEntity
import com.aacademy.domain.entities.Genre

object GenreEntityMapper {

    fun toGenre(genreEntity: GenreEntity) = Genre(genreEntity.id, genreEntity.name)
}
