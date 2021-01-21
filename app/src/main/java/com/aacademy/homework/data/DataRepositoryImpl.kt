package com.aacademy.homework.data

import com.aacademy.homework.data.api.ApiSource
import com.aacademy.homework.data.api.model.MoviesResponse
import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.data.preferences.MyPreference
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    private lateinit var genres: Map<Long, Genre>

    override fun getMovies(query: String?, page: Int): Flow<Pair<Int, List<Movie>>> = flow {
        if (page == 1 && query.isNullOrEmpty()) {
            val localMovies = localSource.getPopularMovies()
            if (localMovies.isNotEmpty())
                emit(Pair(0, localMovies))
        }

        lateinit
        var moviesResponse: MoviesResponse
        coroutineScope {
            launch {
                moviesResponse =
                    if (query.isNullOrEmpty())
                        apiSource.getPopularMovies(page)
                    else
                        apiSource.getMovies(query, page)
            }
            launch {
                if (!::genres.isInitialized)
                    genres = apiSource.getGenres().genres.associateBy { genre -> genre.id }
            }
            launch {
                if (page == 1) {
                    val configuration = apiSource.getConfiguration()
                    myPreference.imageUrl = "${configuration.images.secureBaseURL}w342"
                }
            }
        }
        val result = moviesResponse.results.map { jsonMovie ->
            Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                poster = jsonMovie.posterPicture.orEmpty(),
                backdrop = jsonMovie.backdropPicture.orEmpty(),
                rating = jsonMovie.ratings,
                ageLimit = if (jsonMovie.adult) 16 else 13,
                runtime = 0,
                reviews = jsonMovie.voteCount,
                overview = jsonMovie.overview,
                genres = jsonMovie.genreIds.map { id ->
                    genres[id] ?: throw IllegalArgumentException("Genre not found")
                }
            )
        }
        if (page == 1 && query.isNullOrEmpty())
            localSource.cachePopularMovies(result)
        emit(Pair(moviesResponse.totalPages, result))
    }

    override suspend fun getCast(movieId: Long): Flow<List<Actor>> = flow {
        emit(localSource.getActors(movieId))
        val actors = apiSource.getActors(movieId).cast
        actors.forEach { it.movieId = movieId }
        localSource.cacheActors(actors)
        emit(actors)
    }

    override suspend fun setMovieLiked(movieId: Long, isLiked: Boolean) {
        localSource.setMovieLiked(movieId, isLiked)
    }
}
