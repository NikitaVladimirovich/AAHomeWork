package com.aacademy.homework.mappers

import com.aacademy.domain.entities.Actor
import com.aacademy.homework.entities.ActorParcelable

object ActorParcelableMapper {

    fun toActorParcelable(actor: Actor) =
        ActorParcelable(actor.id, actor.name, actor.profilePath, actor.movieId)
}
