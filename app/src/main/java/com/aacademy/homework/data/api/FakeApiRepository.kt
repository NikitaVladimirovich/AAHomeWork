package com.aacademy.homework.data.api

import com.aacademy.homework.data.local.FakeLocalRepository
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MoviePreviewWithTags
import com.aacademy.homework.data.model.Tag
import io.reactivex.rxjava3.core.Single

object FakeApiRepository {

    fun getAllMoviePreviews(): Single<List<MoviePreviewWithTags>> = Single.just(
        listOf(
            MoviePreview(
                id = 0,
                coverPath = "https://upload.wikimedia.org/wikipedia/en/0/0d/Avengers_Endgame_poster.jpg",
                title = "Avengers: End Game \n Avengers: End Game",
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
        )
    )
        .doOnSuccess { }
        .flatMap { response ->
            response.map { MoviePreviewWithTags(it, it.tags) }.let {
                FakeLocalRepository.cacheMoviePreviewsWithTags(it)
                Single.just(it)
            }
        }
}