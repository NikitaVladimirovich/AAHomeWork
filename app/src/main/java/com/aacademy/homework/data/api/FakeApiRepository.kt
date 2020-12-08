package com.aacademy.homework.data.api

import com.aacademy.homework.data.local.FakeLocalRepository
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MoviePreviewWithTags
import com.aacademy.homework.data.model.Tag

object FakeApiRepository {

    suspend fun getMovieDetail(id: Int): MovieDetailWithActors {
        Thread.sleep(2000)
        return MovieDetail(
            id = id,
            storyline = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            cast = listOf(
                Actor(
                    id = 1 + id * 100,
                    firstName = "Chris",
                    lastName = "Evans",
                    photoPath = "https://static.wikia.nocookie.net/marvelcinematicuniverse/images/b/b2/Chris_Evans.jpg"
                ),
                Actor(
                    id = 2 + id * 100,
                    firstName = "Mark",
                    lastName = "Ruffalo",
                    photoPath = "https://producedbyconference.com/New-York/wp-content/uploads/2019/10/Mark-Ruffalo-600.png"
                ),
                Actor(
                    id = 3 + id * 100,
                    firstName = "Robert",
                    lastName = "Pattinson",
                    photoPath = "https://st.kp.yandex.net/im/kadr/3/5/4/kinopoisk.ru-Robert-Pattinson-3540779.jpg"
                ),
                Actor(
                    id = 4 + id * 100,
                    firstName = "Kenneth",
                    lastName = "Branagh",
                    photoPath = "https://st.kp.yandex.net/im/kadr/1/2/4/kinopoisk.ru-Kenneth-Branagh-1241673.jpg"
                ),
                Actor(
                    id = 5 + id * 100,
                    firstName = "Florence",
                    lastName = "Pugh",
                    photoPath = "https://toronto.citynews.ca/wp-content/blogs.dir/sites/10/2019/06/NYET414-618_2019_013921.jpg"
                )
            ).shuffled()
        ).let {
            val result = MovieDetailWithActors(it, it.cast)
            FakeLocalRepository.cacheMovieDetailWithActors(result)
            result
        }
    }

    suspend fun getAllMoviePreviews(): List<MoviePreviewWithTags> {
        Thread.sleep(2000)
        return listOf(
            MoviePreview(
                id = 0,
                coverPath = "https://upload.wikimedia.org/wikipedia/en/0/0d/Avengers_Endgame_poster.jpg",
                title = "Avengers: End Game",
                rating = 4,
                ageLimit = 13,
                reviews = 135,
                min = 137,
                tags = listOf(
                    Tag(1, "Action"),
                    Tag(2, "Adventure"),
                    Tag(3, "Fantasy")
                )
            ),
            MoviePreview(
                id = 1,
                coverPath = "https://upload.wikimedia.org/wikipedia/en/1/14/Tenet_movie_poster.jpg",
                title = "Tenet",
                rating = 5,
                ageLimit = 16,
                reviews = 98,
                min = 97,
                tags = listOf(
                    Tag(1, "Action"),
                    Tag(4, "Sci-Fi"),
                    Tag(5, "Thriller")
                )
            ),
            MoviePreview(
                id = 2,
                coverPath = "https://terrigen-cdn-dev.marvel.com/content/prod/1x/blackwidow_lob_crd_04.jpg",
                title = "Black Widow",
                rating = 4,
                ageLimit = 13,
                reviews = 38,
                min = 102,
                tags = listOf(
                    Tag(1, "Action"),
                    Tag(2, "Adventure"),
                    Tag(4, "Sci-Fi")
                )
            ),
            MoviePreview(
                id = 3,
                coverPath = "https://upload.wikimedia.org/wikipedia/ru/6/67/Wonder_Woman_1984_%28poster%29.jpg",
                title = "Wonder Woman 1984",
                rating = 5,
                ageLimit = 13,
                reviews = 74,
                min = 120,
                tags = listOf(
                    Tag(1, "Action"),
                    Tag(2, "Adventure"),
                    Tag(3, "Fantasy")
                )
            ),
            MoviePreview(
                id = 4,
                coverPath = "https://upload.wikimedia.org/wikipedia/en/0/0d/Avengers_Endgame_poster.jpg",
                title = "Avengers: End Game",
                rating = 4,
                ageLimit = 13,
                reviews = 135,
                min = 137,
                tags = listOf(
                    Tag(1, "Action"),
                    Tag(2, "Adventure"),
                    Tag(3, "Fantasy")
                )
            ),
            MoviePreview(
                id = 5,
                coverPath = "https://upload.wikimedia.org/wikipedia/en/1/14/Tenet_movie_poster.jpg",
                title = "Tenet",
                rating = 5,
                ageLimit = 16,
                reviews = 98,
                min = 97,
                tags = listOf(
                    Tag(1, "Action"),
                    Tag(4, "Sci-Fi"),
                    Tag(5, "Thriller")
                )
            ),
            MoviePreview(
                id = 6,
                coverPath = "https://terrigen-cdn-dev.marvel.com/content/prod/1x/blackwidow_lob_crd_04.jpg",
                title = "Black Widow",
                rating = 4,
                ageLimit = 13,
                reviews = 38,
                min = 102,
                tags = listOf(
                    Tag(1, "Action"),
                    Tag(2, "Adventure"),
                    Tag(4, "Sci-Fi")
                )
            ),
            MoviePreview(
                id = 7,
                coverPath = "https://upload.wikimedia.org/wikipedia/ru/6/67/Wonder_Woman_1984_%28poster%29.jpg",
                title = "Wonder Woman 1984",
                rating = 5,
                ageLimit = 13,
                reviews = 74,
                min = 120,
                tags = listOf(
                    Tag(1, "Action"),
                    Tag(2, "Adventure"),
                    Tag(3, "Fantasy")
                )
            )
        ).map {
            MoviePreviewWithTags(it, it.tags)
        }.let {
            FakeLocalRepository.cacheMoviePreviewsWithTags(it)
            it
        }
    }
}