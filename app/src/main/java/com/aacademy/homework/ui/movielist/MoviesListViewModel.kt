package com.aacademy.homework.ui.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.foundations.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Collections

class MoviesListViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>> = _movies

    private val moviesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _movies.postValue(Resource.error(throwable.message ?: ""))
    }

    init {
        viewModelScope.launch(dispatcher + moviesExceptionHandler) {
            _movies.postValue(Resource.loading())
            _movies.postValue(Resource.success(dataRepository.getAllPreviews()))
        }
    }

    fun refreshMoviesPreviews() {
        viewModelScope.launch(dispatcher + moviesExceptionHandler) {
            _movies.postValue(Resource.loading())
            _movies.postValue(Resource.success(dataRepository.loadAllPreviews()))
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
