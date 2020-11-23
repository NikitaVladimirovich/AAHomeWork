package com.aacademy.homework.data.local

import com.aacademy.homework.data.local.model.Actor
import com.aacademy.homework.data.local.model.Movie

object MockRepository {

    fun getMovie() = Movie(
        id = 0,
        title = "Avengers: \nEnd Game",
        coverPath = "https://img5.goodfon.ru/original/800x480/6/9c/mstiteli-voina-beskonechnosti-fentezi-poster-personazhi.jpg",
        ageLimit = 16,
        tags = listOf("Action", "Adventure", "Fantasy"),
        rating = 2,
        reviews = 119,
        storyline = "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe.",
        cast = listOf(
            Actor(
                id = 1,
                firstName = "Johnny",
                lastName = "Depp",
                photoPath = "https://i.pinimg.com/236x/d8/ed/b5/d8edb51aa51788b9d3c0360acd6f345a--johnny-depp-blow-heres-johnny.jpg"
            ),
            Actor(
                id = 2,
                firstName = "Johnny",
                lastName = "Depp",
                photoPath = "https://i.pinimg.com/236x/d8/ed/b5/d8edb51aa51788b9d3c0360acd6f345a--johnny-depp-blow-heres-johnny.jpg"
            ),
            Actor(
                id = 3,
                firstName = "Johnny",
                lastName = "Depp",
                photoPath = "https://i.pinimg.com/236x/d8/ed/b5/d8edb51aa51788b9d3c0360acd6f345a--johnny-depp-blow-heres-johnny.jpg"
            ),
            Actor(
                id = 4,
                firstName = "Johnny",
                lastName = "Depp",
                photoPath = "https://i.pinimg.com/236x/d8/ed/b5/d8edb51aa51788b9d3c0360acd6f345a--johnny-depp-blow-heres-johnny.jpg"
            ),
            Actor(
                id = 5,
                firstName = "Johnny",
                lastName = "Depp",
                photoPath = "https://i.pinimg.com/236x/d8/ed/b5/d8edb51aa51788b9d3c0360acd6f345a--johnny-depp-blow-heres-johnny.jpg"
            ),
            Actor(
                id = 6,
                firstName = "Johnny",
                lastName = "Depp",
                photoPath = "https://i.pinimg.com/236x/d8/ed/b5/d8edb51aa51788b9d3c0360acd6f345a--johnny-depp-blow-heres-johnny.jpg"
            )
        )
    )
}
