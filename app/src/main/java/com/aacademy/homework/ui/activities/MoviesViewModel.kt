package com.aacademy.homework.ui.activities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _moviesPreview = MutableLiveData<List<MoviePreviewWithGenres>>()
    val moviesPreview: LiveData<List<MoviePreviewWithGenres>> = _moviesPreview

    private val _movieDetail = MutableLiveData<MovieDetailWithActors>()
    val movieDetail: LiveData<MovieDetailWithActors> = _movieDetail

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val d = dataRepository.getAllPreviews()
            _moviesPreview.postValue(d)
        }
    }

    fun getMovieDetail(id: Long) {
        if (movieDetail.value?.movieDetail?.id != id) {
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                _movieDetail.postValue(dataRepository.getMovieDetail(id))
            }
        }
    }

    fun setMovieLiked(id: Long, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            dataRepository.setMovieLiked(id, isLiked)
        }
    }
}
