package com.aacademy.data.mappers

import com.aacademy.data.model.ActorEntity
import com.aacademy.domain.entities.Actor

object ActorEntityMapper {

    fun toActor(actorEntity: ActorEntity) =
        Actor(actorEntity.id, actorEntity.name, actorEntity.profilePath, actorEntity.movieId)
}
