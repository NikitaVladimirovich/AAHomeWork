package com.aacademy.data

import com.aacademy.data.api.ApiSource
import com.aacademy.data.api.model.MoviesResponse
import com.aacademy.data.local.LocalSource
import com.aacademy.data.mappers.ActorEntityMapper
import com.aacademy.data.mappers.MovieEntityMapper
import com.aacademy.data.model.GenreEntity
import com.aacademy.data.model.MovieEntity
import com.aacademy.data.preferences.MyPreference
import com.aacademy.domain.entities.Actor
import com.aacademy.domain.entities.Movie
import com.aacademy.domain.repositories.DataRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class DataRepositoryImpl @Inject constructor(
    private val apiSource: ApiSource,
    private val localSource: LocalSource,
    private val myPreference: MyPreference
) :
    DataRepository {

    private lateinit var mGenres: Map<Long, GenreEntity>

    override suspend fun getMoviesFromAPI(query: String, page: Int): Pair<Int, List<Movie>> {
        lateinit var moviesResponse: MoviesResponse
        coroutineScope {
            launch {
                moviesResponse =
                    if (query.isEmpty())
                        apiSource.getPopularMovies(page)
                    else
                        apiSource.getMovies(query, page)
            }
            launch {
                if (!::mGenres.isInitialized)
                    mGenres = apiSource.getGenres().genreEntities.associateBy { genre -> genre.id }
            }
            launch {
                if (page == 1) {
                    val configuration = apiSource.getConfiguration()
                    myPreference.imageUrl = "${configuration.images.secureBaseURL}w342"
                }
            }
        }
        val result = moviesResponse.results.map { jsonMovie ->
            MovieEntity(
                id = jsonMovie.id,
                title = jsonMovie.title,
                poster = jsonMovie.posterPicture.orEmpty(),
                backdrop = jsonMovie.backdropPicture.orEmpty(),
                rating = jsonMovie.ratings,
                ageLimit = if (jsonMovie.adult) 16 else 13,
                runtime = 0,
                reviews = jsonMovie.voteCount,
                overview = jsonMovie.overview,
                popularity = jsonMovie.popularity,
                genreEntities = jsonMovie.genreIds.map { id ->
                    mGenres[id] ?: throw IllegalArgumentException("Genre not found")
                }
            )
        }
        localSource.cachePopularMovies(result)
        return Pair(moviesResponse.totalPages, result.map(MovieEntityMapper::toMovie))
    }

    override suspend fun getMoviesFromDB(query: String): List<Movie> =
        localSource.getPopularMovies(query).map(MovieEntityMapper::toMovie)

    override suspend fun getCastFromAPI(movieId: Long): List<Actor> {
        val actors = apiSource.getActors(movieId).cast
        actors.forEach { it.movieId = movieId }
        localSource.cacheActors(movieId, actors)
        return actors.map(ActorEntityMapper::toActor)
    }

    override suspend fun getCastFromDB(movieId: Long): List<Actor> =
        localSource.getActors(movieId).map(ActorEntityMapper::toActor)

    override suspend fun setMovieLiked(movieId: Long, isLiked: Boolean) {
        localSource.setMovieLiked(movieId, isLiked)
    }
}
