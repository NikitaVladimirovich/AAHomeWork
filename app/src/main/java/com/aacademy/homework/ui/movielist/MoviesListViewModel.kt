package com.aacademy.homework.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.domain.usecases.GetApiMoviesUseCase
import com.aacademy.domain.usecases.GetDbMoviesUseCase
import com.aacademy.domain.usecases.LikeMovieUseCase
import com.aacademy.homework.entities.MovieParcelable
import com.aacademy.homework.foundations.Resource
import com.aacademy.homework.mappers.MovieParcelableMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getApiMoviesUseCase: GetApiMoviesUseCase,
    private val getDbMoviesUseCase: GetDbMoviesUseCase,
    private val likeMovieUseCase: LikeMovieUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<MovieParcelable>>>()
    val movies: LiveData<Resource<List<MovieParcelable>>> = _movies

    private val moviesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        if (currentPage == 1)
            _movies.postValue(Resource.error(throwable.message ?: ""))
        isLoading = false
    }

    private var moviesList: MutableList<MovieParcelable> = mutableListOf()
    private var currentPage = 1
    private var isLoading = false
    private var isLastPageLoaded = false

    val queryFlow = MutableStateFlow("")
    var currentJob: Job? = null

    init {
        viewModelScope.launch {
            queryFlow
                .debounce(500)
                .collect {
                    loadFirstPage()
                }
        }
    }

    fun loadFirstPage() {
        currentJob?.cancel()
        currentPage = 1
        isLoading = false
        isLastPageLoaded = false
        loadMovies()
    }

    fun loadMovies() {
        if (!isLoading && !isLastPageLoaded) {
            currentJob = viewModelScope.launch(dispatcher + moviesExceptionHandler) {
                isLoading = true
                if (currentPage == 1)
                    _movies.postValue(Resource.loading(null))
                if (currentPage == 1) {
                    val dbMovies = getDbMoviesUseCase.invoke(queryFlow.value)
                    if (dbMovies.isNotEmpty()) {
                        _movies.postValue(Resource.success(dbMovies.map(MovieParcelableMapper::toMovieParcelable)))
                    }
                }
                val apiMoviesResponse = getApiMoviesUseCase.invoke(queryFlow.value, currentPage)

                if (currentPage == 1) {
                    moviesList =
                        apiMoviesResponse.second.map(MovieParcelableMapper::toMovieParcelable).toMutableList()
                } else {
                    moviesList = moviesList.toMutableList()
                    moviesList.addAll(apiMoviesResponse.second.map(MovieParcelableMapper::toMovieParcelable))
                }
                _movies.postValue(
                    Resource.success(moviesList)
                )
                currentPage++
                isLastPageLoaded = currentPage > apiMoviesResponse.first
                isLoading = false
            }
        }
    }

    fun setMovieLiked(id: Long, isLiked: Boolean) {
        viewModelScope.launch(dispatcher) {
            try {
                likeMovieUseCase.invoke(id, isLiked)
            } catch (thr: Throwable) {
                Timber.e(thr)
            }
        }
    }
}
