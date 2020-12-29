package com.aacademy.homework.ui.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.foundations.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Collections

class MoviesListViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _moviesPreview = MutableLiveData<Resource<List<MoviePreview>>>()
    val moviesPreview: LiveData<Resource<List<MoviePreview>>> = _moviesPreview

    private val moviesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _moviesPreview.postValue(Resource.error(throwable.message ?: ""))
    }

    init {
        viewModelScope.launch(Dispatchers.IO + moviesExceptionHandler) {
            _moviesPreview.postValue(Resource.loading())
            _moviesPreview.postValue(Resource.success(dataRepository.getAllPreviews()))
        }
    }

    fun refreshMoviesPreviews() {
        viewModelScope.launch(Dispatchers.IO + moviesExceptionHandler) {
            _moviesPreview.postValue(Resource.loading())
            _moviesPreview.postValue(Resource.success(dataRepository.loadAllPreviews()))
        }
    }

    fun setMovieLiked(id: Long, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataRepository.setMovieLiked(id, isLiked)
            } catch (thr: Throwable) {
                Timber.e(thr)
            }
        }
    }

    fun swapItems(fromPosition: Int, toPosition: Int) {
        val newMovies = moviesPreview.value!!.data!!.toMutableList()
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(newMovies, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo (toPosition + 1)) {
                Collections.swap(newMovies, i, i - 1)
            }
        }

        _moviesPreview.postValue(Resource.success(newMovies))
    }
}
