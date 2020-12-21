package com.aacademy.homework.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aacademy.homework.data.FakeDataRepository
import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Collections

class MoviesViewModel : ViewModel() {

    private val _moviesPreview = MutableLiveData<List<MoviePreviewWithGenres>>()
    val moviesPreview: LiveData<List<MoviePreviewWithGenres>> = _moviesPreview

    private val _movieDetail = MutableLiveData<MovieDetailWithActors>()
    val movieDetail: LiveData<MovieDetailWithActors> = _movieDetail

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _moviesPreview.postValue(FakeDataRepository.getAllPreviews())
        }
    }

    fun getMovieDetail(id: Long) {
        if (movieDetail.value?.movieDetail?.id != id) {
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                _movieDetail.postValue(FakeDataRepository.getMovieDetail(id))
            }
        }
    }

    fun setMovieLiked(id: Long, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            LocalSource.setMovieLiked(id, isLiked)
        }
    }

    fun swapItems(fromPosition: Int, toPosition: Int) {
        val newMovies = moviesPreview.value!!.toMutableList()
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(newMovies, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo (toPosition + 1)) {
                Collections.swap(newMovies, i, i - 1)
            }
        }

        _moviesPreview.postValue(newMovies)
    }
}
