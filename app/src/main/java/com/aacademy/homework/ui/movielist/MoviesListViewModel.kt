package com.aacademy.homework.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.foundations.Resource
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
import java.util.Collections
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>> = _movies

    private val moviesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        if (currentPage == 1)
            _movies.postValue(Resource.error(throwable.message ?: ""))
        isLoading = false
    }

    private var moviesList: MutableList<Movie> = mutableListOf()
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
                    val dbMovies = dataRepository.getMoviesFromDB(queryFlow.value)
                    if (dbMovies.isNotEmpty()) {
                        _movies.postValue(Resource.success(dbMovies))
                    }
                }
                val apiMoviesResponse = dataRepository.getMoviesFromAPI(queryFlow.value, currentPage)

                if (currentPage == 1) {
                    moviesList = apiMoviesResponse.second.toMutableList()
                } else {
                    moviesList.addAll(apiMoviesResponse.second)
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
                dataRepository.setMovieLiked(id, isLiked)
            } catch (thr: Throwable) {
                Timber.e(thr)
            }
        }
    }

    fun swapItems(fromPosition: Int, toPosition: Int) {
        val newMovies = movies.value!!.data!!.toMutableList()
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(newMovies, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo (toPosition + 1)) {
                Collections.swap(newMovies, i, i - 1)
            }
        }

        _movies.postValue(Resource.success(newMovies))
    }
}
