package com.aacademy.homework.ui.activities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import com.aacademy.homework.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _moviesPreview = MutableLiveData<Resource<List<MoviePreviewWithGenres>>>()
    val moviesPreview: LiveData<Resource<List<MoviePreviewWithGenres>>> = _moviesPreview

    private val _movieDetail = MutableLiveData<Resource<MovieDetailWithActors>>()
    val movieDetail: LiveData<Resource<MovieDetailWithActors>> = _movieDetail

    private val moviesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _moviesPreview.postValue(Resource.error(throwable.message ?: ""))
    }

    private val detailExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _movieDetail.postValue(Resource.error(throwable.message ?: ""))
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
            _moviesPreview.postValue(Resource.success(dataRepository.getAllPreviews(false)))
        }
    }

    fun getMovieDetail(id: Long) {
        if (movieDetail.value?.data?.movieDetail?.id != id) {
            viewModelScope.launch(Dispatchers.IO + detailExceptionHandler) {
                _movieDetail.postValue(Resource.loading())
                _movieDetail.postValue(Resource.success(dataRepository.getMovieDetail(id)))
            }
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
}
